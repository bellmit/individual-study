package cn.gyw.backend.im.netty;

import cn.gyw.backend.im.config.prop.NettyProperties;
import cn.gyw.backend.im.enums.MsgActionEnum;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理消息的handler
 * TextWebSocketFrame：netty中处理ws的文本对象，frame时消息载体
 */
@Component
public class ChannelMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private NettyProperties nettyProperties;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获取客户端传过来的消息
        String content = msg.text();
        Channel curChannel = ctx.channel();

        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();
        if (action == MsgActionEnum.CONNECT.getType()) {
            // websocket 第一次连接时，初始化channel，将channel和userId关联
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.save(senderId, curChannel);
        } else if (action == MsgActionEnum.CHAT.getType()) {
            // 聊天类型消息，记录保存redis，同时标记消息签收状态（未签收）
        } else if (action == MsgActionEnum.KEEPALIVE.getType()) {
            // 心跳类型
            System.out.println("来自心跳包");
        }
    }
}
