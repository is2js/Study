import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

// Simple Web Browser
public class Client {
	
	Socket client;
	public Client(){
		
		try {
			//                     서버IP   , 서버Port
			client = new Socket("127.0.0.1", 8080);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openConnection(){
		// 1. 자원 요청 - 소켓에 정의한 곳으로
		try {
			OutputStream os = client.getOutputStream();
			InputStream is = client.getInputStream();
			String uri = "GET /bbb.jsp HTTP/1.1 \r\n";
			uri += "Host: 127.0.0.1:8080 \r\n";
			uri += "Connection: keep-alive \r\n";
			
			os.write(uri.getBytes());
			os.flush();
			
			// 2. 응답확인
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while( (line = br.readLine()) != null ) {
				System.out.println(line);
			}
			is.close();
			os.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
