package cn.gyw.middleware.zk.lock;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import cn.gyw.platform.common.util.PrintUtil;
import cn.gyw.platform.common.util.PropertiesUtil;

/**
 * 基于永久顺序节点的分布式锁
 * 
 * 说明： 如果有一把锁，多个人竞争，多个人会排队，第一个拿到锁执行，然后释放锁，每个人排队只监听前一个节点，
 * 一旦有人释放了锁，会通知后一个节点，公平锁的思想
 * 
 * 类似：AQS
 */
public class ZookeeperDistributedLock implements Watcher {

	private static final String CONFIG_FILE = "config.properties";

	private ZooKeeper zk;
	private String locksRoot = "/locks";
	private String id;
	private String waitNode;
	private String selfNode;

	private int sessionTimeout = 3000000;
	// 执行栅栏
	private CountDownLatch latch;
	// 保证连接成功后进行操作
	private CountDownLatch connectedLatch = new CountDownLatch(1);

	public ZookeeperDistributedLock(String id) {
		this.id = id;
		Properties properties = PropertiesUtil.readConfig(CONFIG_FILE);
		try {
			zk = new ZooKeeper(properties.getProperty("zk.connect.addr"), sessionTimeout, this);
			connectedLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取分布式锁
	 * 
	 * 阻塞
	 */
	public void acquireDistributedLock() {
		PrintUtil.print("this object :" + this);
		try {
			if (this.tryLock()) {
				return;
			}
			waitForLock(waitNode, sessionTimeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 尝试获取锁
	 * 
	 * @return
	 */
	private Boolean tryLock() {
		try {
			selfNode = zk.create(locksRoot + "/" + id, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);
			// 找到最早的节点
			List<String> locks = zk.getChildren(locksRoot, false);
			Collections.sort(locks);
			if (selfNode.equals(locksRoot + "/" + locks.get(0))) {
				// 最早的节点，获取锁成功
				PrintUtil.print(" 获取锁：" + selfNode);
				return true;
			}
			// 非最小节点，找到前一个节点
			int previousLockIndex = -1;
			for (int i = 1, len = locks.size(); i < len; i++) {
				if (selfNode.equals(locksRoot + "/" + locks.get(i))) {
					previousLockIndex = i - 1;
					break;
				}
			}
			// 需要等待释放锁的节点
			this.waitNode = locks.get(previousLockIndex);
			PrintUtil.print(" 等待节点释放锁：" + waitNode);
			// 判断等待节点是否存在，避免遍历时，锁已释放
			if (this.waitNode == null) {
				// 节点已经释放锁
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 等待锁
	 * 
	 * @param waitNode
	 * @param timeout
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	private boolean waitForLock(String waitNode, int timeout) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(locksRoot + "/" + waitNode, true);
		if (stat != null) {
			latch = new CountDownLatch(1);
			PrintUtil.print("latch init :" + this.latch);
			latch.await(timeout, TimeUnit.MILLISECONDS);
			latch = null;
		}
		return true;
	}

	/**
	 * 释放锁
	 */
	public void unlock() {
		try {
			PrintUtil.print("Unlock " + this.selfNode);
			zk.delete(selfNode, -1);
			selfNode = null;
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行在 EventThread 线程中
	 */
	@Override
	public void process(WatchedEvent event) {
		Event.KeeperState keeperState = event.getState();
		Event.EventType eventType = event.getType();

		switch (keeperState) {
		case SyncConnected:
			if (EventType.None == eventType) {
				PrintUtil.print(" 连接zk 服务器");
				connectedLatch.countDown();
				break;
			}
			PrintUtil.print("keeperState:" + keeperState, "EventType:" + eventType, "Path:" + event.getPath(),
					"waitNode:" + this.waitNode);
			// 节点删除，且是自己等待的节点
			if (EventType.NodeDeleted == eventType && event.getPath().equals(locksRoot + "/" + this.waitNode)) {
				if (this.latch != null) {
					this.latch.countDown();
				}
			}
			break;
		case Disconnected:
			PrintUtil.print("与zk服务器断开连接");
			break;
		case AuthFailed:
			PrintUtil.print("权限验证失败");
			break;
		case Expired:
			PrintUtil.print("会话失败");
			break;
		default:
			PrintUtil.print("不处理的状态：" + keeperState);
		}
	}

}
