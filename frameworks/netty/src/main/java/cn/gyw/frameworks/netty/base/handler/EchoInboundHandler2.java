package cn.gyw.frameworks.netty.base.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class EchoInboundHandler2 extends ChannelInboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(EchoInboundHandler2.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("进入 EchoInboundHandler2.channelRead ");
		String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
		log.info("EchoInboundHandler2.channelRead 接收到数据：{}", data);
		
		// 执行InBound 之前的 OutBound
//		ctx.writeAndFlush(Unpooled.copiedBuffer("test channel().writeAndFlush", CharsetUtil.UTF_8));
		// 执行所有OutBound
		ctx.channel().writeAndFlush(Unpooled.copiedBuffer("test channel().writeAndFlush", CharsetUtil.UTF_8));
		
		ctx.fireChannelRead(Unpooled.copiedBuffer("[EchoInboundHandler2] " + data, CharsetUtil.UTF_8));

		log.info("退出 EchoInboundHandler2.channelRead ");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		log.info("EchoInboundHandler2.channelReadComplete ");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.error("[EchoInboundHandler2.exceptionCaught]" + cause.toString());
	}
}
