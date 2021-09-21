package cn.gyw.frameworks.netty.base;

import cn.gyw.frameworks.netty.base.handler.TestServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.ResourceLeakDetector;

/**
 * 服务端启动类
 * 
 * 拆包、粘包问题分析
 * 
 */
public class TestServer {
	public void bind(int port) {
		// 配置服务端的Nio线程组，boosGroup负责新客户端接入
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		// workerGroup负责I/O消息处理
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(boosGroup, workerGroup)
					// 设置为非阻塞
					.channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
					// 采用匿名内部类的方式，声明hanlder
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {

							/* 粘包解决方案一 */
							// 使用换行符做粘包拆包的处理，所以发送的信息必须加上System.getProperty("line.separator")，否则对方接收不到
							ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
							/* 粘包解决方案二 */
							// 以特殊符号作为一个包的结束符
//							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							
							// 字符串转对象解码器
//							ch.pipeline().addLast(new StringDecoder());

							// 事件处理绑定
							ch.pipeline().addLast(new TestServerHandler());
						}
					});
			// 绑定端口
			ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
			// 等待服务端监听端口关闭
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 优雅退出
			boosGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

	public static void main(String[] argo) {
		new TestServer().bind(8088);
	}
}
