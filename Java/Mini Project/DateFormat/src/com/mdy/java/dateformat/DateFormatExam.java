package com.mdy.java.dateformat;

import java.text.SimpleDateFormat;
import java.util.Date;

class DateFormatExam
{
  public static void main(String[] args)
  {
    Date today = new Date();

    SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yy년 MM월 dd일 E요일");
    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
    SimpleDateFormat sdf5 = new SimpleDateFormat("오늘은 올 해의 D번째 날입니다.");
    SimpleDateFormat sdf6 = new SimpleDateFormat("오늘은 이 달의 d번째 날입니다.");
    SimpleDateFormat sdf7 = new SimpleDateFormat("오늘은 올 해의 w번째 주입니다.");
    SimpleDateFormat sdf8 = new SimpleDateFormat("오늘은 이 달의 W번째 주입니다.");
    SimpleDateFormat sdf9 = new SimpleDateFormat("오늘은 이 달의 F번째 E요일입니다.");

    System.out.println(sdf0.format(today));
    System.out.println(sdf1.format(today));
    System.out.println(sdf2.format(today));
    System.out.println(sdf3.format(today));
    System.out.println(sdf4.format(today));
    System.out.println(sdf5.format(today));
    System.out.println(sdf6.format(today));
    System.out.println(sdf7.format(today));
    System.out.println(sdf8.format(today));
    System.out.println(sdf9.format(today));
  }
}