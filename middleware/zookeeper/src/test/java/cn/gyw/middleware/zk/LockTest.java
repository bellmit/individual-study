package cn.gyw.middleware.zk;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.middleware.zk.lock.ZookeeperDistributedLock;
import cn.gyw.middleware.zk.lock.ZookeeperSession;
import cn.gyw.platform.common.util.PrintUtil;
import cn.gyw.platform.common.util.PropertiesUtil;

/**
 * 分布式锁测试
 */
public class LockTest {

	@Test
	public void testZookeeperDistrubutedLock() {
		PrintUtil.print(" start test ");
		int current = 3;
		CountDownLatch latch = new CountDownLatch(current);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < current; i++) {
			executor.submit(new TestTask2(i, latch, new ZookeeperDistributedLock("1")));
			latch.countDown();
		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testZookeeperSession() {
		ZookeeperSession session = ZookeeperSession.getInstance();
		int current = 10;
		CountDownLatch latch = new CountDownLatch(current);
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < current; i++) {
			executor.submit(new TestTask(i, latch, session));
			latch.countDown();
		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class TestTask2 implements Runnable {

		private int index;
		private CountDownLatch latch;
		private ZookeeperDistributedLock lock;

		public TestTask2(int index, CountDownLatch latch, ZookeeperDistributedLock lock) {
			this.index = index;
			this.latch = latch;
			this.lock = lock;
		}

		@Override
		public void run() {
			try {
				latch.await();
				lock.acquireDistributedLock();
				System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now())
						+ " > task [" + (index + 1) + "] 获取锁...");
				System.out.println("业务处理3s ...");
				TimeUnit.SECONDS.sleep(3L);

				lock.unlock();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private class TestTask implements Runnable {

		private int index;
		private CountDownLatch latch;
		private ZookeeperSession session;

		public TestTask(int index, CountDownLatch latch, ZookeeperSession session) {
			this.index = index;
			this.latch = latch;
			this.session = session;
		}

		@Override
		public void run() {
			try {
				latch.await();
				if (session.acquireDistributedLock(1L)) {
					System.out
							.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now())
									+ " > this task index is :" + (index + 1));
					// 业务处理完成后，释放锁
					session.releaseDistributedLock(1L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Before
	public void prepareData() {
		CountDownLatch connectLatch = new CountDownLatch(1);
		Properties properties = PropertiesUtil.readConfig("config.properties");
		try {
			ZooKeeper zk = new ZooKeeper(properties.getProperty("zk.connect.addr"), 5000, new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					if (Event.KeeperState.SyncConnected == event.getState()) {
						connectLatch.countDown();
					}
				}
			});
			connectLatch.await();
			
			if (zk.exists("/locks", false) != null) {
				zk.getChildren("/locks", false).forEach(path -> {
					try {
						zk.delete("/locks/" + path, -1);
					} catch (Exception e) {
						System.out.println("删除节点失败：" + path);
					}
				});
			} else {
				zk.create("/locks", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
