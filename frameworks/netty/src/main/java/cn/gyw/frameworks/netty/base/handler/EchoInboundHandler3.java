package cn.gyw.frameworks.netty.base.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoInboundHandler3 extends ChannelInboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(EchoInboundHandler3.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("进入 EchoInboundHandler3.channelRead ");
		String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
		log.info("EchoInboundHandler3.channelRead 接收到数据：{}", data);
		// ctx.channel().writeAndFlush(Unpooled.copiedBuffer("测试一下channel().writeAndFlush", CharsetUtil.UTF_8));
		
		ctx.fireChannelRead(msg);

		log.info("退出 EchoInboundHandler3.channelRead ");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.info("EchoInboundHandler3.channelReadComplete ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("[EchoInboundHandler3.exceptionCaught]" + cause.toString());
	}
}
