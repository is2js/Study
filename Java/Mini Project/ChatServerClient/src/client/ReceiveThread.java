package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread {
	
	private Socket socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			BufferedReader tempBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String receiveString;
			
			while(true) {
				receiveString = tempBuffer.readLine();
				
				System.out.println("[Server] : " + receiveString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
