package cn.gyw.corejava.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * 双向读写客户端
 * NIO Client
 * <p>
 * Created by guanyw on 2018/11/8.
 */
public class NClient implements Runnable {

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	// 声明客户端连接通道
	SocketChannel sc = null;

	private long flag = 1;

	// 多路复用器
	private Selector selector;
	// 读缓冲区
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	// 写缓冲区
	private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	// 连接地址
	private InetSocketAddress address;

	// 构造客户端
	public NClient(int port) {
		try {
			// 创建连接地址
			address = new InetSocketAddress("127.0.0.1", 8765);

			// 1.打开多路复用器
			this.selector = Selector.open();
			// 2.打开通道
			sc = SocketChannel.open();
			// 3.设置通道非阻塞模式
			sc.configureBlocking(false);
			// 4.注册通道到Selector上，并监听连接事件
			sc.register(this.selector, SelectionKey.OP_CONNECT);

			// 5.进行链接(必须先注册到Selector上，再连接，否则，Selector无法监听到连接事件)
			sc.connect(address);
			System.out.println("客户端新建连接...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				// 1.让多路复用器开始监听，轮询所有的channel
				// 阻塞到有事件就绪
				this.selector.select();
				// 2.返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
				while (keys.hasNext()) {
					SelectionKey key = keys.next();
					keys.remove();
					if (key.isValid()) {
						// 建立socket连接
						if (key.isConnectable()) {
							System.out.println(key.channel() + " > 连接成功");
							this.socketConnected(key);
						}
						// 可读
						if (key.isReadable()) {
							System.out.println(key.channel() + " > 可读");
							this.read(key);
						}
						// 可写
						if (key.isWritable()) {
							System.out.println(key.channel() + " > 可写");
							this.write(key);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 客户端发送数据给服务端
	 *
	 * @param key
	 */
	private void write(SelectionKey key) {
		try {
			SocketChannel sc = (SocketChannel) key.channel();
			this.writeBuffer.clear();

			// 定义一个字节数组，然后使用系统输入
			byte[] bytes = new byte[1024];
			System.in.read(bytes);

			this.writeBuffer.put(bytes);
			this.writeBuffer.flip();
			sc.write(writeBuffer);
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 服务端返回，客户端通道可读事件
	 *
	 * @param key
	 */
	private void read(SelectionKey key) {
		this.readBuffer.clear();
		SocketChannel sc = (SocketChannel) key.channel();
		try {
			int readByte = sc.read(readBuffer);
			// 如果没有数据
			if (readByte == -1) {
				key.channel().close();
				key.cancel();
				return;
			}
			// 5.有数据则进行读取，读取前进行复位
			this.readBuffer.flip();
			// 6.根据缓冲区的数据长度，创建相应大小的byte数组，接收缓冲区的数据
			byte[] bytes = new byte[this.readBuffer.remaining()];
			// 7.接收缓冲区数据
			this.readBuffer.get(bytes);
			// 8.打印结果
			String body = new String(bytes).trim();
			System.out.println("【客户端】接收到数据：" + body);
			// 继续监听客户端可读事件
			sc.register(this.selector, SelectionKey.OP_WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Socket连接建立事件，已成功连接至服务器
	 */
	private void socketConnected(SelectionKey key) {
		try {
			// 获取连接通道
			SocketChannel sc = (SocketChannel) key.channel();
			if (sc.isConnectionPending()) {
				sc.finishConnect();
				System.out.println("connect success !");

				// 清空缓冲区数据
				writeBuffer.clear();
				// 把数据放到缓冲区
				writeBuffer.put("Hello,Server".getBytes());
				// 缓冲区复位
				writeBuffer.flip();
				// 非阻塞模式下，write方法在尚未写出任何内容时可能就返回了
				sc.write(writeBuffer);
			}
			// 注册通道
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(new NClient(8765)).start();
	}
}
