package cn.gyw.frameworks.netty.base;

import cn.gyw.frameworks.netty.base.handler.TestClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.ResourceLeakDetector;

public class TestClient {

	public void connect(int port, String host) {
		ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
		// 客户端Nio线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			// 线程组设置为非阻塞
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
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
							ch.pipeline().addLast(new TestClientHandler());
						}
					});

			// 建立连接
			ChannelFuture channelFuture = bootstrap.connect(host, port);
			// 等待服务端监听端口关闭
			channelFuture.channel().closeFuture().sync();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 优雅退出
			group.shutdownGracefully();
		}
	}

	public static void main(String[] argo) {
		new TestClient().connect(8088, "localhost");
	}
}
