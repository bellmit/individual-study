package cn.gyw.frameworks.netty.base.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoOutboundHandler1 extends ChannelOutboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(EchoOutboundHandler1.class);
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		log.info("进入 EchoOutboundHandler1.write");
		
		// 绝对不可以调用，否则死循环
		// ctx.channel().writeAndFlush(Unpooled.copiedBuffer("在OutboundHandler里测试一下channel().writeAndFlush", CharsetUtil.UTF_8));
		
		ctx.write(msg);
		
		log.info("退出 EchoOutboundHandler1.write");
	}
}
