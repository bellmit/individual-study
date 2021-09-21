package cn.gyw.frameworks.netty.base.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PlainEchoServer {

	private void serve(int port) throws IOException {
		
		final ServerSocket serverSocket = new ServerSocket(port);
		try {
			while (true) {
				final Socket clientSocket = serverSocket.accept(); // block
				System.out.println("Accepted connection from " + clientSocket);
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
							PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
							while (true) {
								String line = br.readLine();
								System.out.println("Client request params :" + line);
								pw.write(line);
								pw.flush();
							}
						} catch (IOException e) {
							e.printStackTrace();
							try {
								clientSocket.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			serverSocket.close();
		}
	}
	
	public static void main(String[] args) throws IOException {
		new PlainEchoServer().serve(9090);
	}
}
