package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread {
	
	private Socket socket;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		try {
			BufferedReader tempBuffer = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(socket.getOutputStream());
			String sendString;
			
			while(true) {
				sendString = tempBuffer.readLine();
				
				if(sendString.equals("exit")) {
					break;
				}
				
				sendWriter.println(sendString);
				sendWriter.flush();
			}
			
			sendWriter.close();
			tempBuffer.close();
			socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}