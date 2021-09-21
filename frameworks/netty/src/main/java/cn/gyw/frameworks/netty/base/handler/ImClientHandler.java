package cn.gyw.frameworks.netty.base.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ImClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client in active");
        /*JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", 1000);
        jsonObject.addProperty("toId", 1001);
        jsonObject.addProperty("content", "hello this is client");
        ctx.writeAndFlush(Unpooled.copiedBuffer(jsonObject.toString(), CharsetUtil.UTF_8));*/
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("Client received :" + msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
