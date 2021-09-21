package cn.gyw.frameworks.netty.base.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class EchoOutboundHandler3 extends ChannelOutboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(EchoOutboundHandler3.class);
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		log.info("进入 EchoOutboundHandler3.write");
		
		ctx.write(msg);
		
		log.info("退出 EchoOutboundHandler3.write");
	}
}
