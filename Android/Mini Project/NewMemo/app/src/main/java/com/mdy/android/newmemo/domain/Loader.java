package com.mdy.android.newmemo.domain;

import android.content.Context;
import android.util.Log;

import com.mdy.android.newmemo.util.DateUtil;
import com.mdy.android.newmemo.util.FileUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by MDY on 2017-05-29.
 */

public class Loader {
    private static final String TAG = "Loader";
    public static final String FILE_STORAGE = "/data/data/com.mdy.android.newmemo/files";
    public static ArrayList<Memo> datas = new ArrayList<>();

    public static ArrayList<Memo> getData(Context context){
        datas.clear();
        //특정 directory를 File 객체로 생성
        File dir = new File(FILE_STORAGE);

        //특정 directory 내 파일 목록 가져오기
        File files[] = dir.listFiles();
        Log.e(TAG,"files=============================="+files);
        if(files == null)
            return datas; // 파일이 하나도 없으면 그냥 리턴

        for (File file : files) {
            //파일이 directory 가 아닌 file 일때
            if (file.isFile()) {
                Log.i(TAG,"file name=============================="+file.getName());
                Memo memo = new Memo();
                memo.setId(file.getName()); // 파일명 가져오기
                memo.setContent(FileUtil.read(context, file.getName()));
                // 파일 날짜 가져오기 ----------
                long lastModified = file.lastModified(); // 파일에서 마지막 수정일 가져오기
                String formatted = DateUtil.convertLongToString(lastModified); // 날짜 포매팅 하기
                memo.setDate(formatted);
                // ---------------------------
                datas.add(memo);
            }
        }
        return datas;
    }
}