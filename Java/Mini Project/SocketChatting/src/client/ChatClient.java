package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	public static String UserID;
	
	public static void main(String[] args) {
		
		String name = "";
		String ip = "";
		String port = "";
		
		try {
			System.out.println("[이름]을 입력해주세요 : ");
			Scanner scanName = new Scanner(System.in);
			name = scanName.nextLine();
			
			System.out.println("[서버IP]를 입력해주세요 : ");
			Scanner scanIP = new Scanner(System.in);
			ip = scanIP.nextLine();
			
			System.out.println("[서버 Port번호]를 입력해주세요 : ");
			Scanner scanPort = new Scanner(System.in);
			port = scanPort.nextLine();
			int portNum = Integer.valueOf(port);
			

			
			
			Socket c_socket = new Socket(ip, portNum);
			
			ReceiveThread rec_thread = new ReceiveThread();
			rec_thread.setSocket(c_socket);
			
			SendThread send_thread = new SendThread();
			send_thread.setSocket(c_socket);
			
			send_thread.start();
			rec_thread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
