# Chatting
#### Java로 채팅서버를 구현한다.
- **[전체소스코드](https://github.com/mdy0501/Study/blob/master/Java/Mini%20Project/Chatting/src/ChattingMain.java)**

<br>

##### 관련 설명
- IP 는 주소체계이다. IP로는 컴퓨터만 찾을 수 있다.
- 각각의 컴퓨터에 여러개의 프로그램이 있는데 각각의 프로그램과 통신을 하기 위해서는 포트를 열어줘야 한다. 그 열린 포트를 통해서 통신을 할 수 있다. 각 프로그램은 개별적으로 포트를 사용할 수 있다.
- 웹서버의 포트번호 : 80
- 소켓은 IP 주소와 Port가 합쳐진 것이다. ( IP + Port )

#### ChattingMain.java
```java
public class ChattingMain {
	public static void main(String args[]){
		// 서버 생성
		Server server = new Server(10004);
		server.process();

		// 클라이언트 생성
		Client client = new Client();
		client.setConnect();
	}
}
```

#### Client.java
```java
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	Socket socket;
	public Client() {
		try {
			socket = new Socket("192.168.10.240", 10004);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setConnect(){
		// 서브 thread 에서 서버연결을 통해 키보드 입력값을 서버로 전달한다.
		System.out.println("SetConnect");
		Scanner scanner = new Scanner(System.in); // 키보드 입력 생성
		try {
			System.out.println("in try");
			OutputStream os = socket.getOutputStream();
			while(true){
				String word = scanner.nextLine() + "\r\n"; // 입력을 대기하고 있다가 enter 키가 입력되면
				os.write(word.getBytes()); // stream을 통해 서버측으로 전달한다.
				os.flush();
				System.out.println("input:"+word);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
```

#### Server.java
```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket = null;


	// 1. 서버를 생성
	public Server(int port){ // 서버에서 사용할 포트
		try {
			serverSocket = new ServerSocket(port);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 2. 서버동작
	public void process(){
		// 여기서 소켓을 열고 대기
		System.out.println("Server is running...");
		while(true){
			// 3. 서브 thread 에서 소켓을 열고 대기
			Socket socket;
			try {
				socket = serverSocket.accept(); // <- 여기서 프로그램이 멈춘다
				System.out.println(socket.getInetAddress()+" : Connected");
				subProcess(socket); // 서브 thread 에서 소켓을 처리하는 함수로 넘겨준다
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void subProcess(Socket socket){
		new Thread(){
			public void run(){
				try {
					InputStream os = socket.getInputStream();
					System.out.println("opened stream");
					// 스트림을 열고 데이터 통신을 준비
					BufferedReader br = new BufferedReader(new InputStreamReader(os));
					System.out.println("opened buffered");
					// 줄단위로 데이터를 받아서 화면에 출력
					while(true){
						String message= br.readLine();
						System.out.println(socket.getInetAddress()+":"+message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
```
