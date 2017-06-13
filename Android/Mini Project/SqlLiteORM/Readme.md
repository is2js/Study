# SQLite
#### SQLite와 ORM에 대해서 알아본다.

## Query 기본
#### 1. 테이블 생성 쿼리 - DDL(Data Definition Language)
- create table 테이블이름 (컬럼이름1 컬럼속성, 컬럼이름2 속성 ...);
  > - create table memo(memoid int, title varchar(250), content text);
  >   - text : 대용량의 문자를 저장할 수 있음.
  >   - varchar(250) : 250바이트로 된 string

- 데이터 타입
  >  - int : 숫자
  >  - char(250) 	// 문자를 10자만 넣으면 250바이트를 다 채운다.
  >  - varchar(250) 	// 내가 사용할 수 있는 최대 공간 250바이트
  >  - var(변동이 가능한)
  >  - text // 대용량의 데이터를 넣을 수 있다.
  >
  >
- 테이블의 자동증감값 넣기 (2가지 방법)
  > - create table memo2 (memoid **integer primary key**, title varchar(250), content text);
  > - create table memo2 (memoid **integer primary key autoincrement**, title varchar(250), content text);



#### 2. 데이터 입력쿼리 - DML (Data Manipulation Language)
- insert into 테이블이름 (컬럼이름1, 컬럼이름2, ... ) values (숫자값, '문자값' , ... );
  > insert into memo(memoid, title, content) values (1, '제목1', '내용1 입니다');



#### 3. 데이터 조회쿼리
- select 컬럼이름1, 컬럼이름2, ...  from 테이블이름 where 조건절;
  > - select memoid, title from memo;
  >   - 조건절이 없으면 전체 데이터를 가져온다.
  > - select * from memo;
  >   - 컬럼이름 대신에 * 를 사용하면 전체 컬럼을 가져온다.
  > - select memoid, title, content from memo where memoid=1;
  >   - 일반조건절
  > - select * from memo where content like '%내용3%';
  >   - 문자열 중간검색  -  '%검색문자열%'


#### 4. 데이터 수정쿼리
- update 테이블이름 set 컬럼이름=숫자값, 컬럼이름='문자값' where 조건절;  (조건절을 꼭 해줘야 전체 데이터가 바뀌지 않는다.)
  > - update memo set content='수정되었습니다' where memoid=2;
  > - update memo set title='제목 수정', content='수정되었습니다' where memoid=1;



#### 5. 데이터 삭제쿼리
- delete from 테이블이름 where 조건절 (조건절을 써주지 않으면 데이터가 전체삭제된다.)
  > - delete from memo where memoid=2;

<br>
<br>
<br>





## SQLite 사용
#### Gradle 추가
- Group : Artifact : Version 순서로 쓴다.
  - [ex] com.j256.ormlite  /  ormlite-core  /  5.0
- build.gradle (Module:app) 파일의 dependencies 에 다음과 같이 추가한다.
  (참고사이트 : http://mvnrepository.com/artifact/com.j256.ormlite/ormlite-android/4.45 )
```gradle
compile 'com.j256.ormlite:ormlite-core:5.0'
compile 'com.j256.ormlite:ormlite-android:5.0'
```
<br>
<br>





## ORM (Object-Relational Mapping)
- 데이터를 객체처럼 사용할 수 있게 해주는 툴이다.
- ORM이란 용어에도 나와있듯이 객체(Object)와 관계(Relation)을 연결(Mapping)해 주는 개념이다.
객체와 테이블 시스템(RDBMSs)을 변형 및 연결해주는 작업이라 말 할 수 있다.
ORM을 이용한 개발은 객체와 데이터베이스의 변형에 유연하게 대처할 수 있도록 해준다.
ORM을 객체 지향 프로그래밍 관점에서 생각해보면, 관계형 데이터베이스에 제약을 최대한 받지 않으면서, 객체를 클래스로 표현하는 것과 같이 관계형 데이터베이스를 객체처럼 쉽게 표현 또는 사용하자는 것이다.
- Object-Relational Mapping이란 이름 그대로 객체와 관계형 DB를 매핑해주는 것이다. ORM은 객체와 테이블을 매핑하기 때문에 SQL쿼리를 직접 날리는 것이 아니라 마치 자바에서 라이브러리 사용하듯이 사용하면 된다.
- ORM이란, 객체형 데이터(Java의 Object)와 관계형 데이터(관계형 데이터베이스의 테이블) 사이에서 개념적으로 일치하지 않는 부분을 해결하기 위하여 이 둘 사이의 데이터를 매핑(Mapping)하는 것.
객체형 데이터와 관계형 데이터의 각 속성들을 매핑할 경우 관계형 데이터를 객체형 데이터처럼 사용하는 것이 가능하다. 쉽게 말해 SQL문 작성 없이 간단한 매핑 설정으로 데이터베이스의 테이블 데이터를 Java 객체로 전달 받을 수 있는 것이다.
 ORM을 이용하면 개발을 좀 더 편하게 할 수 있고, service layer에 집중할 수 있다.
 대표적으로 Hibernate. iBatis, Spring JPA 등이 있다.





출처(1) : http://blog.naver.com/PostView.nhn?blogId=ljc8808&logNo=220461868128&parentCategoryNo=&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView
출처(2) : http://highlucksw.tistory.com/38
출처(3) : http://annehouse.tistory.com/468
