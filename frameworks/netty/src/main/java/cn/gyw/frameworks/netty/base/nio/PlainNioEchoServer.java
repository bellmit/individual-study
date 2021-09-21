package cn.gyw.frameworks.netty.base.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioEchoServer {

	private void serve(int port) throws IOException {

		final ServerSocketChannel serverChannel = ServerSocketChannel.open();
		ServerSocket ss = serverChannel.socket();
		InetSocketAddress address = new InetSocketAddress(port);
		ss.bind(address);
		serverChannel.configureBlocking(false);
		
		Selector selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while (true) {
			selector.select(); // block
			
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> iter = keys.iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				iter.remove();
				
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel client = server.accept();
					System.out.println("Accepted connection from " + client);
					client.configureBlocking(false);
					client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, ByteBuffer.allocate(100));
				}
				if (key.isReadable()) {
					SocketChannel client = (SocketChannel) key.channel();
					ByteBuffer byteBuf = (ByteBuffer) key.attachment();
					client.read(byteBuf);
				}
				if (key.isWritable()) {
					SocketChannel client = (SocketChannel) key.channel();
					ByteBuffer byteBuf = (ByteBuffer) key.attachment();
					byteBuf.flip();
					client.write(byteBuf);
					byteBuf.compact();
				}
			}
		}
		
	}

	public static void main(String[] args) throws IOException {
		new PlainNioEchoServer().serve(9090);
	}
}
