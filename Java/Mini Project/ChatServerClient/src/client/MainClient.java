package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainClient {
	public static void main(String[] args) {
		try {
			Socket c_socket = new Socket("192.168.111.1", 8888);	// IP, Port
			
			ReceiveThread receiveThread = new ReceiveThread();
			receiveThread.setSocket(c_socket);
			
			SendThread sendThread = new SendThread();
			sendThread.setSocket(c_socket);
			
			sendThread.start();
			receiveThread.start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}