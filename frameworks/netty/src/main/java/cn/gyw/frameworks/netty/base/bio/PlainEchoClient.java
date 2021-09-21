package cn.gyw.frameworks.netty.base.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlainEchoClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket = new Socket("127.0.0.1", 9090);
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("ping");
			bw.flush();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			System.out.println("服务器响应：" + br.readLine());
		} finally {
			socket.close();
		}
		
	}
}
