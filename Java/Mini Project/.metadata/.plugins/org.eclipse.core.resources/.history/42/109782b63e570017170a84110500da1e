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