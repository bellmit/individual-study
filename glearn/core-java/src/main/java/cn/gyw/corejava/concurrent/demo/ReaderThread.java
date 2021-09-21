package cn.gyw.corejava.concurrent.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 取消不可中断的线程
 *
 * 1) SocketIO
 * 2）BIO
 * 3）NIO
 *
 * 方法：
 * 改写interrupt 方法将非标准的取消操作封装在Thread 中
 */
public class ReaderThread extends Thread {

    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            // 关闭底层socket
            socket.close();
        } catch (IOException ignore) {
        } finally {
            // 可以在其他阻塞方法中中断
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    processBuffer(buf, count);
                }
            }
        } catch (IOException e) {
            /* 允许线程退出 */
        }
    }

    private void processBuffer(byte[] buf, int count) {
        /* 处理数据 */
    }
}
