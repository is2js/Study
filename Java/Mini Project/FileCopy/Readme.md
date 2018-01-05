# FileCopy
#### File 복사 실습
- ##### (1) 적당한 대용량의 파일을 준비 (10메가 정도)
- ##### (2) 각 파일을 읽어온 후, 다른 위치에 동일하게 복사

<br>



## Source Code
```java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {

	public static void main(String[] args) {
		FileInputStream input = null;
		FileOutputStream output = null;

		try {
			// 복사할 대상 파일 지정
			File file = new File("C:\\Users\\MDY\\Desktop\\exam01.txt");

			// FileInputStream은 File Object를 생성자 인수로 받을 수 있음
			input = new FileInputStream(file);

			// 복사된 파일의 위치를 지정
			output = new FileOutputStream(new File("C:\\Users\\MDY\\Desktop\\test\\exam02.txt"));

			int readBuffer = 0;
			byte[] buffer = new byte[512];

			System.out.println("파일 복사 시작");

			while((readBuffer = input.read(buffer)) != -1) {
				output.write(buffer, 0, readBuffer);
			}

			System.out.println("파일 복사 완료");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();	// 생성된 InputStream Object를 닫아준다.
				output.close();	// 생성된 OutputStream Object를 닫아준다.
			} catch (IOException io) {
				io.printStackTrace();
			}
		}
	}
}
```
