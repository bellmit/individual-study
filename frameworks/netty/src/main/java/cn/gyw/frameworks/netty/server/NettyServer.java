package cn.gyw.frameworks.netty.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * socket 服务
 * 
 * TODO: 使用 Google protobuf 协议来做序列化 优点： 1）体积更小，传输快 2）性能更好 3）跨语言
 */
@Component
public class NettyServer {

	private static Logger log = LoggerFactory.getLogger(NettyServer.class);

	// 开启两个线程池
	private final EventLoopGroup bossGroup = new NioEventLoopGroup();
	private final EventLoopGroup workGroup = new NioEventLoopGroup();

	// 启动装饰类
	private final ServerBootstrap serverBootstrap = new ServerBootstrap();

	// 本机IP
	private String ip;

	// 启动端口获取
	@Value("${server.port}")
	private int port;

	@Autowired
	private ServerChannelInitializer serverChannelInitializer;
	
	/**
	 * 启动服务
	 */
	@PostConstruct
	public void start() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		serverBootstrap.group(bossGroup, workGroup)
				// 绑定服务端通道
				.channel(NioServerSocketChannel.class)
				// mainReactor 连接缓冲池的大小
				// 用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。
				// 用来初始化服务端可连接队列
				// 服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，
				// 服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
				.option(ChannelOption.SO_BACKLOG, 1024)
				// subReactor 设置通道Channel的分配器
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				// 处理客户端连接之后的请求，处理读写事件，ChannelInitializer是给通道初始化
				.childHandler(serverChannelInitializer);

		ChannelFuture channelFuture = null;
		// 启动成功flag
		boolean startFlag = false;
		while (!startFlag) { // 启动失败，无限重试
			try {
				channelFuture = serverBootstrap.bind(port).sync();
				startFlag = true;
			} catch (InterruptedException e) {
				log.debug("端口号 {} 被占用!", port);
				port++;
				log.debug("启用新端口：{}", port);
				serverBootstrap.localAddress(new InetSocketAddress(port));
			}

		}

		// 服务端启用监听事件
		channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) throws Exception {
				if (future.isSuccess()) {
					log.info("netty 服务启动成功， [{}:{}]", ip, port);
				} else {
					log.warn("netty 服务启动失败， [{}:{}]", ip, port);
				}
			}
		});

		// 监听频道关闭事件
		ChannelFuture closeFuture = channelFuture.channel().closeFuture();
		try {
			// 应用程序会一直等待，直到channel关闭
			closeFuture.sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 释放掉所有资源包括创建的线程
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

	@PreDestroy
	public void destroy() {
		// 释放掉所有资源包括创建的线程
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
}
