# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project context

HubbleRPC is a learning-stage Java RPC framework (`cc.sharper:hubble:2.0-SNAPSHOT`) built on Spring 4 + Netty 4 + Zookeeper, targeting JDK 1.8. The README is in Chinese; user-facing comments throughout the code are also Chinese. Treat this as in-progress work — several pieces (e.g. heartbeat, proxy/http/tcp/service modules) are stubs (`App.java` placeholders).

## Build & run

Maven multi-module build, parent POM at the repository root.

- Build everything: `mvn -f pom.xml clean install`
- Build one module + its dependencies: `mvn -pl core -am clean install`
- **Tests are globally skipped**: every module's `pom.xml` sets `maven-surefire-plugin` `<skip>true</skip>`. To actually run a single test you must override: `mvn -pl <module> -Dmaven.test.skip=false -Dtest=ClassName test`. Most existing `AppTest.java` files are empty stubs from the Maven archetype.
- `show` and `test3` are `war` packagings; no embedded servlet plugin is configured, so deploy them to an external Tomcat (the README mentions Tomcat 8).

## Module layout

| Module | Role |
|---|---|
| `core` | The framework — custom Spring `hubble` namespace, Netty client/server, Zookeeper registry, Protostuff codec |
| `test` | Service interfaces only (`IProvider`, `IProvider2`) — depended on by both sides |
| `test2` | Service implementations (`ProviderImpl`, `ProviderImpl2`) |
| `show` | Provider-side webapp; wires `core` + `test2` via `spring-hubble-provider.xml` |
| `test3` | Consumer-side webapp; wires `core` + `test` via `spring-test.xml` |
| `http`, `proxy`, `service`, `tcp` | Empty `App.java` skeletons — not implemented |

A typical end-to-end run starts a Zookeeper instance, deploys `show` (provider), then `test3` (consumer).

## Framework architecture (the part that requires reading multiple files)

### Custom Spring namespace

The framework is configured entirely through `<hubble:*>` XML elements in user Spring contexts. Wiring lives in `core/src/main/resources/META-INF/`:

- `spring.handlers` maps `http://www.sharper.cc/hubble` → `cc.sharper.HubbleNamespaceHandler`
- `spring.schemas` maps the namespace to `META-INF/hubble.xsd`
- `HubbleNamespaceHandler` registers four parsers: `registry`, `provider`, `consumer`, `server` (in `cc.sharper.Hubble*Parser`), each producing a Spring bean of the corresponding type in `cc.sharper.bean.*`.

When adding a new XML attribute or element, you must touch `hubble.xsd` **and** the matching parser **and** the target bean class — otherwise Spring will silently drop the value.

### Provider (server) startup flow

1. `HubbleServer.afterPropertiesSet()` (`core/.../bean/HubbleServer.java`) boots a Netty `NioServerSocketChannel`. Pipeline: `HubbleDecoder(HubbleRequest)` → `HubbleEncoder(HubbleResponse)` → `RpcServerHandler`.
2. `HubbleProvider.afterPropertiesSet()` registers `interface → host:port` into Zookeeper at `/hubble/provider/<interface>` (ephemeral node, see `ZookeeperUtil.registerService`) and stores `interface → springBeanName` into the static map `ContainProvider.allProvider`.
3. On request, `RpcServerHandler.channelRead0` looks up the bean name in `ContainProvider.allProvider`, fetches the bean from `ApplicationContext`, and invokes via CGLib `FastClass`/`FastMethod` (not plain reflection). It writes the response and immediately closes the channel (`ChannelFutureListener.CLOSE`) — this is one-shot per request, not a long-lived channel.

### Consumer (client) flow

1. `HubbleConsumer` is a Spring `FactoryBean`. Its `getObject()` constructs a `HubbleProxy` and returns a JDK dynamic proxy for the configured interface.
2. On every method call, the `InvocationHandler` builds a `HubbleRequest` (UUID + className + methodName + parameter types/values), asks `ServiceDiscovery.discover()` for an address, then creates a **new** `HubbleClient` (Netty `NioSocketChannel`) per call, sends, and blocks the calling thread on `obj.wait()` until `channelRead0` receives the response.
3. `ServiceDiscovery` opens a Zookeeper watch and picks an entry from `dataList` (random, with a single-element fast path).

### Wire format

Frame = `int32 length` + Protostuff-serialized payload. Encoded by `HubbleEncoder` (`MessageToByteEncoder`), decoded by `HubbleDecoder` (`ByteToMessageDecoder`). Each handler is parameterized by the expected concrete class (`HubbleRequest` or `HubbleResponse`); the server pipeline therefore uses `HubbleDecoder(HubbleRequest.class)` + `HubbleEncoder(HubbleResponse.class)`, and the client pipeline mirrors it. Serialization helpers: `HubbleSerializationUtil`, `ProtostuffUtil`. There is also an unused `HessianUtil` and an `HubbleLengthFieldBasedFrameDecoder` under `codec/`.

## Known landmines (don't "fix" without asking)

These look like bugs but reflect the current learning state of the project:

- `HubbleServer` ignores its configured `port` field and hardcodes `127.0.0.1:8080`.
- `HubbleProxy.invoke` ignores the `serverAddress` returned by `ServiceDiscovery` and dials `127.0.0.1:8080`.
- `ServiceDiscovery.watchNode` watches `HubbleConstant.zookeeper_consumer` (`/hubble/consumer/`), not `/hubble/provider/`, and reads node data from the literal string path `"Constant.ZK_REGISTRY_PATH" + "/" + node`.
- Many methods in `HubbleConsumer` (`refer`, `unrefer`, `afterPropertiesSet`) and the lower half flagged `下面的代码以后要抽离出去` are deliberate placeholders.
- README mentions plans to migrate the registry away from raw Zookeeper to a JDBC + async-callback design ("第二版的注册中心") — current code still uses raw `org.apache.zookeeper.ZooKeeper`.
