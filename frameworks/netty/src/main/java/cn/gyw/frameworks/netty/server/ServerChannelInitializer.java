package cn.gyw.frameworks.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gyw.frameworks.netty.handler.ExceptionHandler;
import cn.gyw.frameworks.netty.handler.HeartBeatServerHandler;
import cn.gyw.frameworks.netty.handler.LoginRequestHandler;
import cn.gyw.frameworks.netty.handler.SimpleServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

@Component
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private LoginRequestHandler loginRequestHandler;

	@Autowired
	private ExceptionHandler exceptionHandler;

	@Autowired
	private SimpleServerHandler simpleServerHandler;
	
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		/*
		 * 粘包解决方案
		 * 固定长度的拆包器 FixedLengthFrameDecoder
		 * 行拆包器 LineBasedFrameDecoder
		 * 分隔符拆包器 DelimiterBasedFrameDecoder
		 * 基于数据包长度的拆包器 LengthFieldBasedFrameDecoder
		 * 
		 * 拆包解决方案
		 * 
		 * */
		
		// 添加编解码和处理器(节点间通讯用)
//		pipeline.addLast("bdeCoder", new ByteArrayDecoder());
//		pipeline.addLast("benCoder", new ByteArrayEncoder());
		// 管理后台登录
		pipeline.addLast("loginHandler", loginRequestHandler);
		pipeline.addLast("simpleServerHandler", simpleServerHandler);

		// 心跳检测
		pipeline.addLast("heartBeat", new HeartBeatServerHandler());
		// 异常处理
		pipeline.addLast("exception", exceptionHandler);
	}

}
