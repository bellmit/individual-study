package cn.gyw.corejava.socket.bio2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {

    private Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            writer = new PrintWriter(this.socket.getOutputStream(), true);

            String body = null;
            while (true) {
                body = reader.readLine();
                if (body == null) {
                    break;
                }
                System.out.println("Server : " + body);
                writer.println("Server response...");
            }
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
                writer.close();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket = null;
        }

    }

}
