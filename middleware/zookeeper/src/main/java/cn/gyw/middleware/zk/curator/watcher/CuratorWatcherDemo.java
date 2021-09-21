package cn.gyw.middleware.zk.curator.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;

/**
 * Curator 创建 Watcher
 * 
 * 由于new TreeCacheListener/NodeCache/PathChildrenCache 新版本已经过时，
 * 使用curatorCache提供的工厂方法 构造treeCacheListener/NodeCache/PathChildrenCache
 * 
 */
public class CuratorWatcherDemo {

	private CuratorFramework client;
	private String rootPath;

	public CuratorWatcherDemo() {
		super();
	}

	public CuratorWatcherDemo(CuratorFramework client, String rootPath) {
		super();
		this.client = client;
		this.rootPath = rootPath;
	}

	public void startListener() {
		CuratorCacheListener listener = CuratorCacheListener.builder()
				.forPathChildrenCache(rootPath, client, (c, e) -> {
					// child node listener
					System.out.println("child node change :" + e.getType());
				}).forNodeCache(() -> {
					System.out.println("Called when a change has occurred");
				}).forTreeCache(client, (c, e) -> {
					System.out.println("Status changed :" + e.getType());
					switch (e.getType()) {
					case INITIALIZED:
						onInit(e);
						break;
					case NODE_ADDED:
						onAdded(e);
						break;
					case NODE_UPDATED:
						onUpdated(e);
						break;
					case NODE_REMOVED:
						onRemoved(e);
						break;
					default:
						break;
					}
				}).build();
		CuratorCache cache = CuratorCache.builder(client, rootPath).build();
		System.out.println("CuratorCache building complete ,listener path :" + rootPath);
		cache.listenable().addListener(listener);
		cache.start();
	}

	private void onRemoved(TreeCacheEvent e) {
		// TODO Auto-generated method stub

	}

	private void onUpdated(TreeCacheEvent e) {
		// TODO Auto-generated method stub

	}

	private void onAdded(TreeCacheEvent e) {
		// TODO Auto-generated method stub

	}

	private void onInit(TreeCacheEvent e) {
		// TODO Auto-generated method stub

	}
}
