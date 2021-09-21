package cn.gyw.frameworks.netty.base;

import java.net.InetSocketAddress;

import cn.gyw.frameworks.netty.base.handler.EchoInboundHandler1;
import cn.gyw.frameworks.netty.base.handler.EchoInboundHandler2;
import cn.gyw.frameworks.netty.base.handler.EchoInboundHandler3;
import cn.gyw.frameworks.netty.base.handler.EchoOutboundHandler1;
import cn.gyw.frameworks.netty.base.handler.EchoOutboundHandler2;
import cn.gyw.frameworks.netty.base.handler.EchoOutboundHandler3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		new EchoServer(9999).start();
	}

	public void start() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();   //创建ServerBootstrap
			b.group(bossGroup, workGroup)
					.channel(NioServerSocketChannel.class)   //指定NIO的传输Channel
					.localAddress(new InetSocketAddress(port))
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							//outboundhandler一定要放在最后一个inboundhandler之前
                            //否则outboundhandler将不会执行到

                            socketChannel.pipeline().addLast(new EchoOutboundHandler3());
                            socketChannel.pipeline().addLast(new EchoInboundHandler1());
                            
                            socketChannel.pipeline().addLast(new EchoOutboundHandler2());
                            socketChannel.pipeline().addLast(new EchoInboundHandler2());
                            
                            socketChannel.pipeline().addLast(new EchoOutboundHandler1());
                            socketChannel.pipeline().addLast(new EchoInboundHandler3());
						}
					});
			ChannelFuture future = b.bind().sync();  //绑定服务器，sync等待服务器关闭
			System.out.println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully().sync();
			workGroup.shutdownGracefully().sync();
		}
	}
}
