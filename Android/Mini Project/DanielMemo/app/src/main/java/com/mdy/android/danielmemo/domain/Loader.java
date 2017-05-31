package com.mdy.android.danielmemo.domain;

import android.content.Context;
import android.widget.Toast;

import com.mdy.android.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by MDY on 2017-05-31.
 */

public class Loader {
    // 파일이 저장되는 디렉토리(Internal Storage) 경로 = /data/data/패키지명/files
    public static final String DIR_PATH = "/data/data/com.mdy.android.danielmemo/files";

    // 1. 메모를 저장한 디렉토리를 리스팅해서 파일 목록과
    // 2. 해당 파일의 내용 첫줄
    // 3. 해당 파일의 수정일자를 담아서 리턴한다.

    public static ArrayList<Memo> datas = new ArrayList<>();    // 예를 들면, 195 주소값이 저장되면 앞으로 계속 주소값이 고정

    public static ArrayList<Memo> getData(Context context){ // readFirstLine을 사용하기 위해서 getDate에 context를 인자로 넘겨준다.

        // 이전의 데이터를 삭제하고
        datas.clear();
        // 아래에서 데이터를 다시 입력해준다.


        // 1.1 목록을 가져올 디렉토리경로를 파일 클래스로 생성하고
        File dir = new File(DIR_PATH);

        // 1.2 파일 클래스에 정의된 listFiles 함수를 이용해서 파일목록을 가져온다.
        // listFiles()를 사용하면 리턴값이 파일에 배열로 넘어온다.
        File fIies[] = dir.listFiles();

        // 1.3 파일이 하나도 없으면 그냥 리턴한다.
        if(fIies == null)
            return datas;

        // 파일이 있을 경우,
        // 2.1 반복문을 돌면서 파일의 내용을 Memo 객체에 담은후 datas에 add한다.
        for(File file : fIies){
            // 2.2 파일이면 (디렉토리일 경우는 안함)
            if(file.isFile()) {
                Memo memo = new Memo();
                // 2.2.1 파일명을 가져와서 담는다.
                memo.setId(file.getName());
                // 2.2.2 작성일자를 formatting해서 문자열로 담는다.
                String formatted = convertLongToDate(file.lastModified());
                memo.setDate( formatted );
                // 2.2.3 파일의 첫줄만 가져와서 담는다.
                String firstLine = FileUtil.readFirstLine(context, file.getName()); // readFirstLine을 사용하기 위해서 getDate에 context를 인자로 넘겨받는다.
                memo.setContent(firstLine);




                datas.add(memo);
            }
        }

        return datas;
    }

    public static String convertLongToDate(long value){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf.format(value);
    }




    public static void deleteData(Context context, String fileName) {


        try {
            File dir = new File(DIR_PATH);

            // 1.2 파일 클래스에 정의된 listFiles 함수를 이용해서 파일목록을 가져온다.
            // listFiles()를 사용하면 리턴값이 파일에 배열로 넘어온다.
            File files[] = dir.listFiles();


            for (int i = 0; i < files.length; i++) {
                String fName = files[i].getName();
                if (fName.equals(fileName)) {
                    files[i].delete();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "파일 삭제 실패", Toast.LENGTH_SHORT).show();
        }
    }


}