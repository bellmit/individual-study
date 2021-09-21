package cn.gyw.corejava.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的Socket编程
 * 特点：
 * 1.同步的
 * 2.阻塞的
 */
public class BServer {

    final static int port = 8765;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println(" server start ...");
            while (true) {
                // 进行阻塞
                Socket socket = serverSocket.accept();
                // 新建一个线程处理客户端的任务
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serverSocket = null;
        }
    }
}
