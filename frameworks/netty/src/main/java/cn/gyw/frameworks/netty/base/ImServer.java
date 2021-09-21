package cn.gyw.frameworks.netty.base;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import cn.gyw.frameworks.netty.base.handler.ImServerHandler;
import cn.gyw.frameworks.netty.model.ImProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * IM Server
 */
public class ImServer {

    private int port;

    public ImServer(int port) {
        this.port = port;
    }

    private void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ImMessageDecoder())
                                    .addLast(new ImServerHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // keep long connect

            // Start the server.
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out
                    .println(EchoServer.class.getName() + " started and listen on " + future.channel().localAddress());
            if (future.isSuccess()) {
                System.out.println("Netty start success.");
            }
            // Wait until the server socket is closed.
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class ImMessageDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            System.out.println("ImMessageDecoder decode ...");

            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            String content = new String(bytes);
            System.out.println("in content:" + content);
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(content, JsonObject.class);

            ImProtocol imProtocol = new ImProtocol();
            imProtocol.setUid(jsonObject.get("uid").getAsLong());
            imProtocol.setUid(jsonObject.get("toId").getAsLong());
            imProtocol.setContent(jsonObject.get("content").getAsString());

            out.add(in);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new ImServer(9090).start();
    }
}
