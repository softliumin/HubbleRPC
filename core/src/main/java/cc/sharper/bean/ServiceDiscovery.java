package cc.sharper.bean;

import cc.sharper.HubbleConstant;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liumin3 on 2016/9/14.
 */
public class ServiceDiscovery
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDiscovery.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<String>();// 多线程都可以看到此地址信息列表

    private String registryAddress;

    public ServiceDiscovery(String registryAddress) {
        this.registryAddress = registryAddress;

        ZooKeeper zk = connectServer();
        if (zk != null) {
           // watchNode(zk);
        }
    }

    public String discover(String name) {
        String data = null;
        int size = dataList.size();
        if (size > 0) {
            if (size == 1)
            {

                data = dataList.get(0);
                LOGGER.debug("using only data: {}", data);

            } else
            {
                data = dataList.get(ThreadLocalRandom.current().nextInt(size));
                LOGGER.debug("using random data: {}", data);
            }
        }
        return data;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, HubbleConstant.zookeeper_timeout, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            LOGGER.error("", e);
        }catch (InterruptedException e)
        {
            LOGGER.error("", e);
        }
        return zk;
    }

    //注册监听节点的数据的变化
    private void watchNode(final ZooKeeper zk)
    {
        try
        {
            //注册一个事件 观察 ZK_REGISTRY_PATH 节点的子节点
            List<String> nodeList = zk.getChildren(HubbleConstant.zookeeper_consumer, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<String>();
            for (String node : nodeList)
            {
                byte[] bytes = zk.getData("Constant.ZK_REGISTRY_PATH" + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            LOGGER.debug("node data: {}", dataList);
            this.dataList = dataList;
        } catch (KeeperException e)
        {
            LOGGER.error("", e);
        }catch (InterruptedException e)
        {
            LOGGER.error("", e);
        }
    }
}
