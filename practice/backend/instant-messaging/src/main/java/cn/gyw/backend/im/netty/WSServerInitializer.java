package cn.gyw.backend.im.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class WSServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // http 支持
        // websocket 基于http协议，需要http编解码器
        pipeline.addLast(new HttpServerCodec());
        // 写大数据流支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 心跳支持
        // 针对客户端，如果1分钟没有发送读写心跳，主动断开；如果时读写空闲，不处理
        pipeline.addLast(new IdleStateHandler(8, 10, 12));
        // 自定义空闲状态检测
        pipeline.addLast(new HeartBeatHandler());

        // websocket 支持
        // websocket 服务器处理的协议，该handler处理复杂的事：握手，心跳，以Frame抽象传输数据
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        // 自定义handler
        pipeline.addLast(new ChannelMessageHandler());
    }
}
