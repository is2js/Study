package com.mdy.android.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by MDY on 2017-05-31.
 */

public class FileUtil {

    // <파일읽기>  Internal Storage를 사용할 경우에만
    public static String read(Context context, String filename){
        String result = "";
        try {
            // 1. 스트림을 열고
            FileInputStream fis = context.openFileInput(filename);
            // 2. 래퍼가 필요할 경우는 사용 (래퍼의 용도는 문자열 캐릭터셋을 변환(인코딩)해주는 역할)
            //   - 생략가능 (항상 UTF-8을 사용하면)

            // 3. 버퍼를 씌워서 속도를 향상시킨후 한 줄씩 읽어서 result 결과값에 계속 더해준다.
            BufferedInputStream bis = new BufferedInputStream(fis);

            // 4. 내가 한번에 읽어올 단위를 설정
            byte buffer[] = new byte[1024];

            int count = 0;
            // 내가 버퍼 단위로 읽어왔는데 내가 읽은 버퍼안에 몇글자가 있는지 확인하기 위해서
            // 버퍼로 떴는데 몇글자가 들어있는지를 count에 담아준다.
            // buffer에 글자가 담긴다.

            while( (count = bis.read(buffer)) != -1 ) {

                String data = new String(buffer, 0, count);
                // 0부터 count까지만 데이터로 스트링으로 변환을 해준다.
                // 그래서 마지막에 1024 크기 안에 100개의 크기만 담겼다면

                result = result + data;
            }

            // 4. 스트림을 역순으로 닫는다.


            // 3. 스트림을 닫아주면 된다.
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return result;
    }




    // 파일 첫줄만 읽기
    public static final String readFirstLine(Context context, String filename){
        String result = "";

        // <Reader 사용해보기> 텍스트파일을 읽기 위한 reader를 사용하기 위해서

        try {
            // 1. 스트림을 열고
            FileInputStream fis = context.openFileInput(filename);

            // 2. BufferedReader에 담기 위해서 Stream을 Reader에서 읽을 수 있는 형태로 변경한다.
            InputStreamReader isr = new InputStreamReader(fis);

            // 3. 텍스트 파일을 읽기 위한 Reader 계열의 버퍼에 담고
            BufferedReader br = new BufferedReader(isr);
            // 4. 한줄을 읽은 후 result 에 저장하고
            result = br.readLine();   // 순차적으로 close()를 해준다.
            // 5. 스트림을 닫는다.
//            br.close();
//            isr.close();
            fis.close();

        } catch (Exception e){
            Log.e(TAG, e.toString());
        }


        return result;
    }






    // [interal storage]에 저장
    // tools - android - android device monitor 를 누르면 파일이 저장된 것을 볼 수 있다.
    // data - data - com.mdy.android.memo2 (패키지명) - files 에 보면 파일이 저장된 것을 확인할 수 있다.
    // <파일에 쓰기>
    public static void write(Context context, String filename, String value){
        try {
            // 1. 스트림을 열어야 됩니다.
            FileOutputStream fos = context.openFileOutput(filename, MODE_PRIVATE);   // openFileOutput가 스트림을 열어주는 역할을 한다.
            // 2. 스트림을 통해서 데이터를 쓰고
            fos.write(value.getBytes());
            fos.close();
            // 3. 스트림을 닫아준다.
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }
}
