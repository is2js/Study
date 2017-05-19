package com.mdy.mybbs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	// 클래스에 속성은 없고, 기능만 들어가 있는 것들이 Util 패키지에 들어간다.
	// Util에 들어가는 메소드들은 대부분 public으로 선언된다.
	// Util에 들어가는 메소드들(util성 메소드)은 static으로 사용한다.
	public static void makeDirectoryIfNotExist(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists()){
			dir.mkdirs(); // 검사한 경로상의 모든 디렉토리를 생성해준다.
		}
	}
	
	public static File getFile(String path){
		File database = new File(path);
		if(!database.exists()){
			makeFile(database);
			database = new File(path);
		}
		return database;
	}
	
	public static void makeFile(File database){
		try {
			database.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static String readFileForCount(File database){
		String number = "";
		try {
			FileReader fr = new FileReader(database); // 1. 읽기위한 얇은 빨대 꽂기(숟가락)
			BufferedReader br = new BufferedReader(fr); // 2. 굵은 빨대
			number = br.readLine(); // 3. 한줄만 읽어오기
			fr.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return number;
	}
	
	public static void writeFileForCount(File database, long result){
		try {
			FileWriter fw = new FileWriter(database); 
			fw.write(result+"");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}






