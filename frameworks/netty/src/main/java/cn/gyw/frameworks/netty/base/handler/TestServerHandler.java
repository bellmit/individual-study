package cn.gyw.frameworks.netty.base.handler;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class TestServerHandler extends ChannelInboundHandlerAdapter {

	private static int counter;

	// I/O消息的接收处理
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 模拟粘包现象
//		mockStickPkg(ctx, msg);
		// 粘包解决方案1：行拆包器
//		stickPkgSolution1(ctx, msg);
//		stickPkgSolution2(ctx, msg);
		
		// 模拟拆包现象
		mockUnpack(ctx, msg);
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 遇到异常时关闭ChannelHandlerContext
		ctx.close();
	}

	private void mockStickPkg(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buf = (ByteBuf) msg;
			byte[] req = new byte[buf.readableBytes()];
			buf.readBytes(req);
			String body = new String(req, "UTF-8").substring(0,
					req.length - System.getProperty("line.separator").length());
			// 把接收到的内容输出到控制台
			System.out.println("the server receive [" + body + " ], the count is " + (++counter));
			String currentTime = "current time:".equals(body) ? new Date(System.currentTimeMillis()).toString()
					: "BAD ORDER";
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			// 返回信息给客户端
			ctx.writeAndFlush(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void stickPkgSolution1(ChannelHandlerContext ctx, Object msg) {
		try {
			// 此处已经LineBasedFrameDecoder、StringDecoder的解码，可以直接接收
			String body = (String) msg;
			// 把接收到的内容输出
			System.out.println("the server receive [" + body + " ], the count is " + (++counter));
			String currentTime = "current time:".equals(body) ? new Date(System.currentTimeMillis()).toString()
					: "BAD ORDER";
			// 发送的消息，必须加换行符，否则客户端接收不到
			currentTime = currentTime + System.getProperty("line.separator");
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			// 返回信息给客户端
			ctx.writeAndFlush(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void stickPkgSolution2(ChannelHandlerContext ctx, Object msg) {
		try {
			// 此处已经LineBasedFrameDecoder、StringDecoder的解码，可以直接接收
			String body = (String) msg;
			// 把接收到的内容输出
			System.out.println("the server receive [" + body + " ], the count is " + (++counter));
			String currentTime = "current time:".equals(body) ? new Date(System.currentTimeMillis()).toString()
					: "BAD ORDER";
			// 发送的消息
			currentTime = currentTime + "$_";
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			// 返回信息给客户端
			ctx.writeAndFlush(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void mockUnpack(ChannelHandlerContext ctx, Object msg) {
		try {
			ByteBuf buf = (ByteBuf) msg;
			String body = buf.toString(CharsetUtil.UTF_8);
			
			// 把接收到的内容输出到控制台
			System.out.println("the server receive [" + body + " ], the count is " + (++counter));
			String currentTime = "current time:".equals(body) ? new Date(System.currentTimeMillis()).toString()
					: "BAD ORDER";
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			// 返回信息给客户端
			ctx.writeAndFlush(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
