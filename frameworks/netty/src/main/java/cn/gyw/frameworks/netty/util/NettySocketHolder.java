package cn.gyw.frameworks.netty.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty socket holder
 */
public class NettySocketHolder {

	// 长链接存储
	private static final Map<Long, NioSocketChannel> SOCKETS_MAP = new ConcurrentHashMap<>();

	public static void put(Long uid, NioSocketChannel socketChannel) {
		SOCKETS_MAP.put(uid, socketChannel);
	}

	public static NioSocketChannel get(Long uid) {
		return SOCKETS_MAP.get(uid);
	}

	public static void remove(NioSocketChannel socketChannel) {
		SOCKETS_MAP.entrySet().stream().filter(entry -> entry.getValue() == socketChannel)
				.forEach(entry -> SOCKETS_MAP.remove(entry.getKey()));
	}
}
