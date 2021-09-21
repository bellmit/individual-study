package cn.gyw.frameworks.netty.base.handler;

import cn.gyw.frameworks.netty.util.NettySocketHolder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ImServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Server is in active...");
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, ByteBuf imProtocol) throws Exception {
		System.out.println("Client send :" + imProtocol);
		//NettySocketHolder.put(imProtocol.getUid(), (NioSocketChannel) ctx.channel());
		//sendMessage(imProtocol.getToId(), imProtocol.getContent());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client inactive.");
		NettySocketHolder.remove((NioSocketChannel) ctx.channel());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// 出现异常关闭连接
		ctx.close();
	}
	
	private void sendMessage(Long toId, String content) {
		SocketChannel socketChannel = NettySocketHolder.get(toId);
		socketChannel.writeAndFlush(content);
	}
}
