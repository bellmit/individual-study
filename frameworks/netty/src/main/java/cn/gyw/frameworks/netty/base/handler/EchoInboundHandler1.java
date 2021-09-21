package cn.gyw.frameworks.netty.base.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoInboundHandler1 extends ChannelInboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(EchoInboundHandler1.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("进入 EchoInboundHandler1.channelRead ");
		String data = ((ByteBuf)msg).toString(CharsetUtil.UTF_8);
		log.info("EchoInboundHandler1.channelRead 接收到数据：{}", data);
		
		// 是否调用了fire* 方法决定了是否调用下一个handler
		ctx.fireChannelRead(Unpooled.copiedBuffer("[EchoInboundHandler1] " + data, CharsetUtil.UTF_8));
		
		log.info("退出 EchoInboundHandler1.channelRead ");
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.info("EchoInboundHandler1.channelReadComplete ");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("[EchoInboundHandler1.exceptionCaught]" + cause.toString());
	}
}
