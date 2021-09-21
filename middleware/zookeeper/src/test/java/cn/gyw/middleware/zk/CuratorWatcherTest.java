package cn.gyw.middleware.zk;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gyw.middleware.zk.config.ZkConfig;
import cn.gyw.middleware.zk.curator.watcher.CuratorWatcherDemo;

/**
 * Watcher 测试验证
 */
public class CuratorWatcherTest {

	/** session超时时间 */
	static final int SESSION_OUTTIME = 5000;// ms
	private CuratorFramework cf;

	@Test
	public void watchNodeChange() throws Exception {
		// 创建的默认的Watcher，会话级的
		new CuratorWatcherDemo(cf, "/super").startListener();

		Thread.sleep(1000);
		cf.create().forPath("/super", "123".getBytes());

		System.out.println("getData() 客户端注册一个Watcher：one watcher");
		// 注册1个一次性的Watcher
		cf.getData().usingWatcher(new CuratorWatcher() {
			@Override
			public void process(WatchedEvent event) throws Exception {
				System.out.println("【one watcher】 evnet :" + event.getPath() + ", " + event.getType());
			}
		}).forPath("/super");

		Thread.sleep(1000);
		System.out.println("第一次触发setData()，必然存在one watcher 被回调");
		cf.setData().forPath("/super", "456".getBytes());
		
		Thread.sleep(1000);
		System.out.println("第二次触发setData()，不存在one watcher 被回调，验证watcher 的一次性");
		cf.setData().forPath("/super", "456".getBytes());

		Thread.sleep(1000);
		cf.delete().forPath("/super");

		TimeUnit.MINUTES.sleep(3L);
	}

	/**
	 * 子节点变化
	 * 
	 * @throws Exception
	 */
	@Test
	public void watchNodePathChange() throws Exception {
		// 创建的默认的Watcher，会话级的
		new CuratorWatcherDemo(cf, "/super").startListener();

		// 创建本身节点不发生变化
		cf.create().forPath("/super", "init".getBytes());

		// 添加子节点
		Thread.sleep(1000);
		cf.create().forPath("/super/c1", "c1内容".getBytes());
		Thread.sleep(1000);
		cf.create().forPath("/super/c2", "c2内容".getBytes());

		// 修改子节点
		Thread.sleep(1000);
		cf.setData().forPath("/super/c1", "c1更新内容".getBytes());

		// 删除子节点
		Thread.sleep(1000);
		cf.delete().forPath("/super/c2");

		// 删除本身节点
		Thread.sleep(1000);
		cf.delete().deletingChildrenIfNeeded().forPath("/super");

		Thread.sleep(Integer.MAX_VALUE);

	}

	@Before
	public void before() {
		// 1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		// 2 通过工厂创建连接
		cf = CuratorFrameworkFactory.builder().connectString(ZkConfig.INSTANCE.getZkServer())
				.sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy).build();

		// 3 建立连接
		cf.start();
	}

	@After
	public void after() {
		cf.close();
	}
}
