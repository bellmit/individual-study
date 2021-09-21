package cn.gyw.middleware.zk.zookeeper.base;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * Zookeeper 原生API 学习
 */
public class ZookeeperBase {

    /** zookeeper地址 */
    static final String CONNECT_ADDR = "192.168.1.181:2181,192.168.1.182:2181,192.168.1.183:2181";
    /** session 超时时间 */
    static final int SESSION_OUTTIME = 5000; // ms

    static final CountDownLatch connectedSemphore = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 获取事件的状态
                Event.KeeperState keeperState = watchedEvent.getState();
                Event.EventType eventType = watchedEvent.getType();
                //如果是建立连接
                if(Event.KeeperState.SyncConnected == keeperState){
                    if(Event.EventType.None == eventType){
                        //如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                        connectedSemphore.countDown();
                        System.out.println("zk 建立连接");
                    }
                }
            }
        });

        connectedSemphore.await();

        System.out.println("...");

        //创建父节点
        zk.create("/testRoot", "testRoot".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //创建子节点
//		zk.create("/testRoot/children", "children data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        //获取节点洗信息
//		byte[] data = zk.getData("/testRoot", false, null);
//		System.out.println(new String(data));
//		System.out.println(zk.getChildren("/testRoot", false));

        //修改节点的值
//		zk.setData("/testRoot", "modify data root".getBytes(), -1);
//		byte[] data = zk.getData("/testRoot", false, null);
//		System.out.println(new String(data));

        //判断节点是否存在
//		System.out.println(zk.exists("/testRoot/children", false));
        //删除节点
//		zk.delete("/testRoot", -1, new AsyncCallback.VoidCallback() {
//            @Override
//            public void processResult(int i, String s, Object o) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(i);
//                System.out.println(s);
//                System.out.println(o);
//            }
//        }, "a");
//		System.out.println(zk.exists("/testRoot/children", false));
//        System.out.println("flag");

        Thread.sleep(3000);

        zk.close();
    }

}
