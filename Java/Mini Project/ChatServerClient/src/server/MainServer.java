package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8888);
			
			Socket clientSocket = serverSocket.accept();
			
			ReceiveThread receiveThread = new ReceiveThread();
			receiveThread.setSocket(clientSocket);
			
			SendThread sendThread = new SendThread();
			sendThread.setSocket(clientSocket);
			
			receiveThread.start();
			sendThread.start();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
