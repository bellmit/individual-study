package cn.gyw.corejava.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * 双向读写服务端
 * NIO Socket 服务端
 * （启用单线程 轮训 Selector 注册的 Channel）
 * Created by guanyw on 2018/11/8.
 * <p>
 * 说明：
 * 1.Selector可以监听四种不同的集合：Connect、Accept、Read、Write；
 * 2.通道触发了一个事件意思是该事件已经就绪。
 * 某个channel成功连接到另一个服务器称为“连接就绪”。
 * 一个server socket channel准备好接收新进入的连接称为“接收就绪”。
 * 一个有数据可读的通道可以说是“读就绪”。
 * 等待写数据的通道可以说是“写就绪”。
 */
public class NServer implements Runnable {

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	// 多路复用器
	private Selector selector;
	// 读缓冲区
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	// 写缓冲区
	private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

	// 构造Server端
	public NServer(int port) {
		try {
			// 1.打开多路复用器
			this.selector = Selector.open();
			// 2.打开服务器通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			// 3.设置服务器通道为非阻塞模式
			ssc.configureBlocking(false);
			// 4.绑定地址
			ssc.bind(new InetSocketAddress(port));
			// 5.把服务器端的通道注册到多路复用器上，并且监听阻塞事件(Interest集合)
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);

			System.out.println("Server start ! port : " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		System.out.println("--------------------------");
		// 轮询 Selector，发现符合监听的通道
		while (true) {
			try {
				// 1.让多路复用器开始监听，轮询所有的channel
				this.selector.select();
				// 2.返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
				// 3.进行遍历
				while (keys.hasNext()) {
					// 4.获取一个选择的元素
					SelectionKey key = keys.next();
					// 5.直接从容器中移除
					keys.remove();
					// 6.如果是有效的
					if (key.isValid()) {
						if (key.isConnectable()) {
							System.out.println("监听的事件" + key.interestOps());
						}
						// 7.如果是阻塞的
						if (key.isAcceptable()) {
							System.out.println(key.channel() + " > 新连接阻塞");
							this.accept(key);
						}
						// 8.如果是可读的
						if (key.isReadable()) {
							System.out.println(key.channel() + " > 可读");
							this.read(key);
						}
						// 9.如果是可写的
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

	private void write(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		try {
			this.writeBuffer.clear();
			// 响应客户端
			String helloStr = "sever send to client : " + sdf.format(new Date());
			this.writeBuffer.put(helloStr.getBytes());
			this.writeBuffer.flip();
			while (writeBuffer.hasRemaining()) {
				sc.write(writeBuffer);
			}
			System.out.println("【服务端】发送数据：" + helloStr);
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 可读事件
	 * 服务端接收到客户端发送的数据，读取数据后继续监听客户端
	 *
	 * @param key
	 */
	private void read(SelectionKey key) {
		try {
			// 1.清空缓冲区的旧数据
			this.readBuffer.clear();
			// 2.获取之前注册的Socket通道对象
			SocketChannel sc = (SocketChannel) key.channel();
			// 3.读取数据
			int count = sc.read(readBuffer);
			// 4.如果没有数据
			if (count == -1) {
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
			System.out.println("Server :" + body);

			// 响应客户端
			// 9.可以写回给客户端数据
         	sc.register(this.selector, SelectionKey.OP_WRITE);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("关闭Socket连接：" + key.channel());
			try {
				key.channel().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			key.cancel();
		}
	}

	/**
	 * 客户端连接事件
	 * 新连接创建=》服务器通道阻塞，获取客户端通道
	 * 1.ServerSocket与客户端建立socket连接，将此socket注册到Selector中，并监听其Read事件；
	 * 2.当客户端发来数据，并被服务端线程正确读取时，此时该通道为Read，触发selector可读事件
	 *
	 * @param key
	 */
	private void accept(SelectionKey key) {
		try {
			// 1.获取服务通道
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			// 2.执行阻塞方法
			SocketChannel sc = ssc.accept();
			// 3.设置阻塞模式
			sc.configureBlocking(false);
			// 4.注册到多路复用器上，并设置读取标识
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Thread(new NServer(8765)).start();
	}

}
