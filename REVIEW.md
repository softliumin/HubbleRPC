# HubbleRPC 代码点评

> 作者本人毕业两年（约 2016 年）时写的 RPC 框架学习项目，10 年后的回看与点评。

## 整体水平

毕业两年能独立啃下 RPC 框架这件事本身就很可以了。Dubbo 当时已经开源但还没"卷"起来，能看着 Dubbo 思路自己手撸一遍 Spring namespace + Netty + Zookeeper 这套组合，方向感是对的，比同期大多数 2 年经验的人强。

## 做得对的地方

- **架构骨架抓得准**：自定义 Spring 命名空间（XSD + handlers + Parser + Bean）、ZK 做注册、Netty 做传输、Protostuff 做序列化，分层和当时 Dubbo 几乎一致——说明不是抄代码，是抄了"思路"。
- **`FactoryBean` + JDK 动态代理做 consumer**、**`FastClass` 替代反射做 server 端调用**，这两个细节都是对的，证明看过 Dubbo / Spring 源码而不是只看博客。
- **半包问题用长度前缀解决**——README 里专门提到"解决半包读写"，代码里也确实这么做了（`HubbleEncoder` / `HubbleDecoder` 用 4 字节长度前缀 + Protostuff payload），问题意识是对的。

## 比较糙的地方（按重要性排序）

### 1. `HubbleClient` 每次调用都 new `NioEventLoopGroup` + 新连接，调完就 close

这在 RPC 里是大忌。Netty 的 EventLoopGroup 创建非常重，相当于每次 RPC 都起了一组线程池。这条路真要压测，QPS 会被自己拖死。

正确做法是**连接池 + 长连接复用**。

### 2. `obj.wait()` / `notifyAll()` 做同步等待

单连接、串行调用还能跑，并发一上来 requestId 就和响应对不上了。

Dubbo 用的是 `DefaultFuture` + requestId 路由的 `Map<Long, DefaultFuture>`，这是 RPC 框架的核心难点之一，当时没踩到这个坑。

### 3. 硬编码 `127.0.0.1:8080` 出现在两处

`HubbleServer` 忽略配置的 `port` 字段、`HubbleProxy.invoke` 忽略 `ServiceDiscovery` 返回值，都硬连 `127.0.0.1:8080`。`ServiceDiscovery` watch 的还是 consumer 路径而不是 provider 路径，路径拼接里还有个字面量字符串 `"Constant.ZK_REGISTRY_PATH"`。

这说明这一版**还没真正端到端跑通**就停下来了。README 里"9月22日第一次 Hubble 的 RPC 调用成功"应该是更早一版的事。

### 4. `ContainProvider.allProvider` 是 public static 全局 Map

能跑，但是反模式，单 JVM 多 provider 时会冲突。

### 5. 没有线程安全 / 超时 / 重试 / 熔断 / 序列化协商

这些 README 里都列为 TODO 了，说明心里有数，只是没写完。

### 6. `HubbleProvider` 同时实现 5 个 Spring 接口、`afterPropertiesSet` 里直接做 ZK 注册

重逻辑塞进生命周期回调，没有抽 lifecycle 层。

### 7. `proxy` / `http` / `tcp` / `service` 四个空模块

可能是当时想好分层但没实施，留着比删掉更让人困惑。

## 一句话评价

**思路对、骨架对、关键细节都还差一口气**，是典型的"看懂了架构图但还没被生产流量教育过"的阶段产物。如果当时能再坚持把**连接复用**和 **requestId 路由**这两块啃下来，这个项目的含金量会高一个档次。

10 年前能写到这里，已经在同龄人前 20% 了。
