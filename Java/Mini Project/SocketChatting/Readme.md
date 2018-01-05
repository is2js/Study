# Java Socket programming
#### Socket Programming
- ##### (1) 서버는 Port 번호를 입력받는다.
- ##### (2) 클라이언트는 이름, 서버IP, 서버Port번호를 입력받는다.
- ##### (3) 서버는 하나의 채팅방을 만들며, 클라이언트들은 실시간으로 접속 및 해지가 가능하다.
- ##### (4) 클라이언트가 접속 혹은 해지가 되면 다른 클라이언트에게 상황을 알려야 한다.
- ##### (5) 하나의 클라이언트에서 보낸 메시지는 다른 모든 클라이언트에게 전송되어야 한다.

<br>


## 결과 화면
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_01.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_02.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_03.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_04.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_05.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_06.png)
![SocketChatting](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/SocketChatting/graphics/result_07.png)

<br>


## Source Code
### [Server]

##### [1] ChatServer.class
```java
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
```

<br>

##### [2] ClientManagerThread.class
```java
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManagerThread extends Thread {

	private Socket m_socket;
	private String m_ID;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {
			BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));

			String text;

			while(true) {
				text = tmpbuffer.readLine();

				if(text == null) {
					System.out.println(m_ID + "이(가) 나갔습니다.");
					for(int i=0; i<ChatServer.m_OutputList.size(); ++i) {
						ChatServer.m_OutputList.get(i).println(m_ID + "이(가) 나갔습니다.");
						ChatServer.m_OutputList.get(i).flush();
					}
					break;
				}

				String[] split = text.split("mdy12345");
				if(split.length == 2 && split[0].equals("ID")) {
					m_ID = split[1];
					System.out.println(m_ID + "이(가) 입장하였습니다.");
					for(int i=0; i<ChatServer.m_OutputList.size(); ++i) {
						ChatServer.m_OutputList.get(i).println(m_ID + "이(가) 입장하였습니다.");
						ChatServer.m_OutputList.get(i).flush();
					}
					continue;
				}

				for(int i=0; i<ChatServer.m_OutputList.size(); ++i) {
					ChatServer.m_OutputList.get(i).println(m_ID + "> " + text);
					ChatServer.m_OutputList.get(i).flush();
				}
			}

			ChatServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
			m_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void setSocket(Socket _socket) {
		m_socket = _socket;
	}
}
```

<br>


### [Client]

##### [1] ChatClient.java
```java
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
```

<br>

##### [2] ReceiveThread.java
```java
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiveThread extends Thread {

	private Socket m_Socket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(m_Socket.getInputStream()));

			String receiveString;
			String[] split;

			while(true) {
				receiveString = tmpbuf.readLine();

				split = receiveString.split(">");
				if(split.length >= 2 && split[0].equals(ChatClient.UserID)) {
					continue;
				}

				System.out.println(receiveString);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket _socket) {
		m_Socket = _socket;
	}
}
```

<br>

##### [3] SendThread.java
```java
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendThread extends Thread {

	private Socket m_Socket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(m_Socket.getOutputStream());
			String sendString;

			System.out.println("사용할 ID를 입력해주십시오 : ");
			ChatClient.UserID = tmpbuf.readLine();

			sendWriter.println("IDmdy12345" + ChatClient.UserID);
			sendWriter.flush();

			while(true) {
				sendString = tmpbuf.readLine();

				if(sendString.equals("exit")) {
					break;
				}

				sendWriter.println(sendString);
				sendWriter.flush();
			}

			sendWriter.close();
			tmpbuf.close();
			m_Socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket _socket) {
		m_Socket = _socket;
	}

}
```
