package cn.gyw.frameworks.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
@Component
public class ExceptionHandler extends ChannelInboundHandlerAdapter  {
	
	private static Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof Exception) {
            //捕捉异常信息
            cause.printStackTrace();
            log.error(cause.getMessage());
            ctx.close();
        } else {
            //捕捉异常信息
            cause.printStackTrace();
            log.error(cause.getMessage());
            ctx.close();
        }
	}

}
