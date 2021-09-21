package cn.gyw.middleware.zk.lock;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import cn.gyw.platform.common.util.PropertiesUtil;

public class ZookeeperSession {

	private static CountDownLatch connectedSemaphore = new CountDownLatch(1);

	private static final String CONFIG_FILE = "config.properties";

	private int sessionTimeout = 50000;

	private long waitTime = 30;
	
	private static final String LOCK_PREFIX = "/distributed-lock";

	private ZooKeeper zookeeper;

	private CountDownLatch latch;

	public ZookeeperSession() {
		Properties properties = PropertiesUtil.readConfig(CONFIG_FILE);
		try {
			this.zookeeper = new ZooKeeper(properties.getProperty("zk.connect.addr"), sessionTimeout,
					new ZookeeperWatcher());

			connectedSemaphore.await();
			
			Stat stat = this.zookeeper.exists(LOCK_PREFIX, false);
			if (stat == null) {
				this.zookeeper.create(LOCK_PREFIX, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				System.out.println("创建根节点成功：" + LOCK_PREFIX);
			}
			System.out.println("Zookeeper session established...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取单例
	 * @return
	 */
	public static ZookeeperSession getInstance() {
		return Singleton.getInstance();
	}

	/**
	 * 获取分布式锁
	 * 
	 * @param uid 唯一标识
	 * @return
	 */
	public Boolean acquireDistributedLock(Long uid) {
		String path = LOCK_PREFIX + "/" + uid;
		try {
			// 创建临时节点
			this.zookeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			return true;
		} catch (Exception e) {
			// 创建失败
			while (true) {
				try {
					// 相当于是给node 注册一个监听器
					Stat stat = zookeeper.exists(path, true);
					if (stat != null) {
						this.latch = new CountDownLatch(1);
						this.latch.await(waitTime, TimeUnit.SECONDS);
						this.latch = null;
					}
					// 尝试创建临时节点
					this.zookeeper.create(path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
					return true;
				} catch (Exception e1) {
//					System.out.println("尝试创建临时节点失败，重试");
					continue;
				}
			}
		}
	}
	
	/**
	 * 释放分布式锁
	 * @param uid
	 */
	public void releaseDistributedLock(Long uid) {
		String path = LOCK_PREFIX + "/" + uid;
		try {
			this.zookeeper.delete(path, -1);
			System.out.println("release the lock for uid : " + uid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class ZookeeperWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			System.out.println("Received watched event:" + event.getState());
			if (KeeperState.SyncConnected == event.getState()) {
				connectedSemaphore.countDown();
			}
			if (latch != null) {
				latch.countDown();
			}
		}
	}
	
	private static class Singleton {
		private static ZookeeperSession instance;
		
		static {
			instance = new ZookeeperSession();
		}
		
		public static ZookeeperSession getInstance() {
			return instance;
		}
	}

}
