package com.lsgf;

import org.apache.zookeeper.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;

import static org.springframework.boot.SpringApplication.run;


@SpringBootApplication
public class RedisLockApplication {
    public static void main(String[] args) {
        run(RedisLockApplication.class, args);
        try {
            zkRegister();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static void zkRegister() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();

//        EnvironmentUtil envUtil = new EnvironmentUtil();
//        String port = envUtil.getPort();
//        String webApp = envUtil.getWebApp();
//        String zkIp=envUtil.getConfig("zk.ip");

        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            //zk集群配置
            /*
            ZooKeeper zooKeeper=
                    new ZooKeeper("localhost:2181," +
                            "192.168.3.35:2181,192.168.3.37:2181",
                            4000, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if(Event.KeeperState.SyncConnected==event.getState()){
                                //如果收到了服务端的响应事件，连接成功
                                countDownLatch.countDown();
                            }
                        }
                    });
             */
            ZooKeeper zooKeeper =
                    new ZooKeeper("10.0.19.168:2181",
                            4000, new Watcher() {
                        @Override
                        public void process(WatchedEvent event) {
                            if (Event.KeeperState.SyncConnected == event.getState()) {
                                //如果收到了服务端的响应事件，连接成功
                                countDownLatch.countDown();
                            }
                        }
                    });

            countDownLatch.await();
            //CONNECTED
            System.out.println(zooKeeper.getState());
            //检查服务是否已经注册
            if (zooKeeper.exists("/generate-incr-no", false) == null) {
                zooKeeper.create("/generate-incr-no", ip.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
            }
            //添加节点
            zooKeeper.create("/generate-incr-no/" + ip, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
