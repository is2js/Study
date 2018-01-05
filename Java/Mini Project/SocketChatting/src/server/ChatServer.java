package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServer {
	
	public static ArrayList<PrintWriter> m_OutputList;
	
	public static void main(String[] args) {
		
		m_OutputList = new ArrayList<PrintWriter>();
		
		try {
			System.out.println("[서버 Port 번호]를 입력해주세요 : ");
			Scanner scanServerPort = new Scanner(System.in);
			String port = scanServerPort.nextLine();
			int serverPortNum = Integer.valueOf(port);
			
			ServerSocket s_socket = new ServerSocket(serverPortNum);
			
			while(true) {
				Socket c_socket = s_socket.accept();
				ClientManagerThread c_thread = new ClientManagerThread();
				c_thread.setSocket(c_socket);
				
				m_OutputList.add(new PrintWriter(c_socket.getOutputStream()));
				
				c_thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
