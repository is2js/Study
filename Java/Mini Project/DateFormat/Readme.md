### 2017.05.29(월)
---
#### SimpleDateFormat 패턴 및 사용예제  (출처: 자바의 정석)
---

* #### 설명 (순서: 기호 - 의미 - 보기)
  - G - 연대(BC, AD) - AD
  - Y - 년도 - 2017
  - M - 월(1~12 또는 1월~12월) - 5또는 5월, MAY
  - w - 년의 몇 번째 주(1~53) - 22
  - W - 월의 몇 번째 주(1~5) - 5
  - D - 년의 몇 번째 일(1~366) - 149
  - d - 월의 몇 번째 일(1~31) - 29
  - F - 월의 몇 번째 요일(1~5) - 5
  - E - 요일 - 월
  - a - 오전/오후(AM,PM) - 오후
  - H - 시간(0~23) - 22
  - k - 시간(1~24)
  - K - 시간(0~11)
  - h - 시간(1~12) - 10
  - m - 분(0~59) - 11
  - s - 초(0~59) - 41
  - S - 천분의 1초(0~999) - 141
  - z - Time zone(General time zone)
  - Z - Time zone(RFC 822 time zone)

* #### [예제]
```java
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
```
* #### [결과]
  20170529
  2017-05-29
  17년 05월 29일 월요일
  2017-05-29 22:11:41.141
  2017-05-29 10:11:41 오후
  오늘은 올 해의 149번째 날입니다.
  오늘은 이 달의 29번째 날입니다.
  오늘은 올 해의 22번째 주입니다.
  오늘은 이 달의 5번째 주입니다.
  오늘은 이 달의 5번째 월요일입니다.
---
