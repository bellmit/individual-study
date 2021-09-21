package cn.gyw.frameworks.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
@Component
public class LoginRequestHandler extends ChannelInboundHandlerAdapter {

	private static Logger log = LoggerFactory.getLogger(LoginRequestHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf data = (ByteBuf) msg;
		log.debug("LoginRequestHandler channelRead, read : {}", data.toString(CharsetUtil.UTF_8));
		
		ctx.fireChannelRead(msg);
	}
}
