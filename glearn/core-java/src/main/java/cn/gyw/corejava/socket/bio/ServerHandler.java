package cn.gyw.corejava.socket.bio;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("当前线程：" + Thread.currentThread().getName());
        BufferedReader reader = null;
        Writer writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            String body = null;
            while (true) {
                body = reader.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("Server receive : " + body);
            }
            OutputStream out = this.socket.getOutputStream();
//            writeString(writer, out);
            writeFile(out);

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket = null;
        }

    }

    private void writeString(Writer writer, OutputStream out) throws IOException {
        writer = new PrintWriter(out, true);
        writer.write("服务器回调函数...");
    }

    private void writeFile(OutputStream out) throws IOException {
        byte[] buf = new byte[2048];
        int len = 0;
        InputStream fis = null;
        try {
            fis = new FileInputStream(new File("D:\\Temp\\web\\hello.html"));
            while ((len = fis.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
