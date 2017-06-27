# HttpBbs
### Tomcat을 이용한 서버통신

<br>

### [전체소스코드](https://github.com/mdy0501/Study/blob/master/Android/Mini%20Project/HttpBbs/app/src/main/java/com/mdy/android/httpbbs/MainActivity.java)

<br>

- #### location.href 와 location.replace의 차이점

|    | location.href  | location.replace |
| :----- | :---- | :--- |
| 기능  | 새로운 페이지로 이동된다.  | 기존 페이지를 새로운 페이지로 변경시킨다. |
| 형태  | 속성  | 메서드 |
| 주소 히스토리  | 기록된다.  | 기록되지 않는다.  |
| 사용 예시  | location.href='abc.php'  | location.replace('abc.php')  |


<br>
<Br>

## 기준일: 2017.06.27.화
### 파일 구조
| Depth (1)   | Depth (2)  | Depth (3) | Depth (4)|
| :----: | :----: | :----: | :----: |
| **bbs(폴더)**  | [1] index.jsp  |  |  |
|   | [2] insert.jsp  |  |  |
|   | [3] write.html  |  |  |
|   | **json(폴더)**  | **insert(폴더)**  | [4] index.jsp |
|   |   | **list(폴더)**  | [5] index.jsp |

## [1] index.jsp [ROOT\bbs\index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>목록</title>

	<script language="javascript">
		function goWrite() {
			// alert("sendData");
			location.href = "write.html";
			// 미리 정의된 내장 객체
		}
	</script>

</head>
<body>

<h1>목록</h1>
<p>
<a href="javascript:goWrite()">글쓰기</a>
</p>
<%
	// 데이터베이스 연결
	Class.forName("com.mysql.jdbc.Driver");
		// 드라이버 클래스를 동적으로 로드
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
	Connection con = DriverManager.getConnection(url, "root", "mysql");

	Statement stmt = con.createStatement();
	String query = "select * from board";

	// 데이터 가져오기
	ResultSet cursor = stmt.executeQuery(query);	// ResultSet은 Content Resolver의 cursor처럼 실행됨.
	while(cursor.next()){
		int id = cursor.getInt("id");
		String title = cursor.getString("title");
		String author = cursor.getString("author");
		String content = cursor.getString("content");
		String date = cursor.getString("date");

		out.print(id + "|" + title + "|" + author + "|" + content + "|" + date + "<br/>");
	}

	con.close();

%>
</body>
</html>
```

<br>

## [2] insert.jsp [ROOT\bbs\insert.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="com.google.gson.*" %>

<%!
	class Data {
		String title;
		String author;
		String content;
	}
%>

<%
	// 요청값에 대한 한글처리
	request.setCharacterEncoding("utf-8");

	// json 데이터를 받아서
	String json = request.getParameter("json");

	out.println(json);

	// json 오브젝트로 변경하고
	Gson gson = new Gson();
	Data data = gson.fromJson(json, Data.class);



	// Database 연결
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
												// 데이터베이스의 주소, 유저아이디, 패스워드
	Connection con = DriverManager.getConnection(url, "root", "mysql");


	String query =
		"insert into board(title, author, content, date) value( ?, ?, ?, now())";

	PreparedStatement pstmt = con.prepareStatement(query);
	pstmt.setString(1, data.title);	// index가 1부터 시작
	pstmt.setString(2, data.author);
	pstmt.setString(3, data.content);
	pstmt.executeUpdate();		// statement 할때, stmt.execute(query);의 역할

	con.close();

%>

<html>
<head>
	<meta charset="utf-8">
</head>
<body>

<h1>
성공적으로 전송하셨습니다.
</h1>
<a href="/bbs">목록으로</a>

</body>
</html>
```

<br>

## [3] write.html [ROOT\bbs\write.html]
```java
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>글쓰기</title>

	<script language="javascript">
		// 함수선언 방법
		// function 함수명(파라미터, 파라미터) { }	// 인자에 타입이 들어가지 않는다.
		function sendData() {
			// 변수선언 방법
			// var 변수명;
			var title = document.getElementById("title");	// document.getElementById - 내장객체, API
			var author = document.getElementById("author");
			var content = document.getElementById("content");

			var jsonString = '{ ';
			jsonString = jsonString + '  title : "'+title.value+'"';
			jsonString = jsonString + ', author : "'+author.value+'"';
			jsonString = jsonString + ', content : "'+content.value+'"';
			jsonString = jsonString + '}'

			document.getElementById("json").value = jsonString;

			// form을 서버로 전송
			document.getElementById("form").submit();
		}
	</script>

</head>
<body>

<h1>글쓰기</h1>
<p>새글을 입력해주세요.</p>
<p>
<form name="form" id="form" method="post" action="/bbs/insert.jsp" onsubmit="return false;" />
	<input type="hidden" name="json" id="json" />
제목: <input type="text" name="title" id="title" size="30" value="" /> <br/>
작성자: <input type="text" name="author" id="author" size="30"/> <br/>
내용:  <textarea name="content" id="content" cols="30" rows="5"/></textarea> <br/>

<input type="button" onclick="sendData()" value="전송"/>

</form>
</p>


</body>
</html>
```

<br>

## [4] index.jsp [ROOT\bbs\json\insert\index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="com.google.gson.*" %>

<%!
	class Data {
		int id;
		String title;
		String author;
		String content;
		// String date;
	}
%>

<%
	// 요청값에 대한 한글처리
	request.setCharacterEncoding("utf-8");

	// json 데이터를 받아서
	String json = request.getParameter("jsonString");

	out.println(json);

	// json 오브젝트로 변경하고
	Gson gson = new Gson();
	Data data = gson.fromJson(json, Data.class);



	// Database 연결
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
												// 데이터베이스의 주소, 유저아이디, 패스워드
	Connection con = DriverManager.getConnection(url, "root", "mysql");


	String query =
		"insert into board(title, author, content, date) value( ?, ?, ?, now())";

	PreparedStatement pstmt = con.prepareStatement(query);
	pstmt.setString(1, data.title);	// index가 1부터 시작
	pstmt.setString(2, data.author);
	pstmt.setString(3, data.content);
	pstmt.executeUpdate();		// statement 할때, stmt.execute(query);의 역할

	con.close();

%>

<html>
<head>
	<meta charset="utf-8">
</head>
<body>

<h1>
성공적으로 전송하셨습니다.
</h1>

</body>
</html>
```

<br>

## [5] index.jsp [ROOT\bbs\json\list\index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.google.gson.*" %>

<%
	// 1. 데이터베이스 연결
	Class.forName("com.mysql.jdbc.Driver");
		// 드라이버 클래스를 동적으로 로드
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
	Connection con = DriverManager.getConnection(url, "root", "mysql");

	Statement stmt = con.createStatement();
	String query = "select * from board";

	// 2. 쿼리를 실행해서 데이터 가져오기
	ResultSet cursor = stmt.executeQuery(query);	// ResultSet은 Content Resolver의 cursor처럼 실행됨.


	// json String 만들기
	// String jsonString = " { bbsList: [ ";
	// 안에 내용
	// 수동으로 json String 변경
		// out.print("{");
		// out.print("id:" + id + ", title:\"" + title + "\", author:\"" + author + "\", content:\"" + content + ", date:" + date + "");
		// out.print("}");
	// jsonString


	// 3. 데이터 반복문으로 list 저장소에 담기
	List<Bbs> list = new ArrayList<>();

	while(cursor.next()){
		int id = cursor.getInt("id");
		String title = cursor.getString("title");
		String author = cursor.getString("author");
		String content = cursor.getString("content");
		String date = cursor.getString("date");

		Bbs data = new Bbs();
		data.id = id;
		data.title = title;
		data.author = author;
		data.content = content;
		data.date = date;

		list.add(data);

	}

	cursor.close();

	con.close();


	// Data data = new Data();
	// data.bbsList = list;


	// 4. list의 데이터를 json 스트링으로 전환하기
	Gson gson = new Gson();
	String jsonString = gson.toJson(list);
	out.print("{\"bbsList\":" + jsonString + "}");

%>

<%!
	class Data {
		List<Bbs> bbsList;
	}

	class Bbs{
		int id;				// 숫자 타입에는 null값 입력 안됨. (json에서 java로 넘어올때 exception이 나서 프로그램이 죽는다.)
		String title;
		String author;
		String content;
		String date;
	}
%>
```




<br>


---

<br>

## JsonString 사용

- json 사용
```javascript
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>글쓰기</title>

	<script language="javascript">
		// 함수선언 방법
		// function 함수명(파라미터, 파라미터) { }	// 인자에 타입이 들어가지 않는다.
		function sendData() {
			// 변수선언 방법
			// var 변수명;
			var title = document.getElementById("title").value;	// document.getElementById - 내장객체, API
			var author = document.getElementById("author").value;
			var content = document.getElementById("content").value;

			var jsonString = '{ ';
			jsonString = jsonString + '  title : "'+title+'"';
			jsonString = jsonString + ', author : "'+author+'"';
			jsonString = jsonString + ', content : "'+content+'"';
			jsonString = jsonString + '}'

			alert(jsonString);
		}
	</script>

</head>
<body>

<h1>글쓰기</h1>
<p>새글을 입력해주세요.</p>
<p>
<form method="get" action="/bbs/insert.jsp" onsubmit="return false;" />
제목: <input type="text" name="title" id="title" size="30" value="" /> <br/>
작성자: <input type="text" name="author" id="author" size="30"/> <br/>
내용:  <textarea name="content" id="content" cols="30" rows="5"/></textarea> <br/>

<input type="button" onclick="sendData()" value="전송"/>

</form>
</p>


</body>
</html>
```

- json2
```javascript
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>글쓰기</title>

	<script language="javascript">
		// 함수선언 방법
		// function 함수명(파라미터, 파라미터) { }	// 인자에 타입이 들어가지 않는다.
		function sendData() {
			// 변수선언 방법
			// var 변수명;
			var title = document.getElementById("title");	// document.getElementById - 내장객체, API
			var author = document.getElementById("author");
			var content = document.getElementById("content");

			var jsonString = '{ ';
			jsonString = jsonString + '  title : "'+title.value+'"';
			jsonString = jsonString + ', author : "'+author.value+'"';
			jsonString = jsonString + ', content : "'+content.value+'"';
			jsonString = jsonString + '}'

			document.getElementById("json").value = jsonString;

			// form을 서버로 전송
			document.getElementById("form").submit();
		}
	</script>

</head>
<body>

<h1>글쓰기</h1>
<p>새글을 입력해주세요.</p>
<p>
<form name="form" id="form" method="get" action="/bbs/insert.jsp" onsubmit="return false;" />
	<input type="hidden" name="json" id="json" />
제목: <input type="text" name="title" id="title" size="30" value="" /> <br/>
작성자: <input type="text" name="author" id="author" size="30"/> <br/>
내용:  <textarea name="content" id="content" cols="30" rows="5"/></textarea> <br/>

<input type="button" onclick="sendData()" value="전송"/>

</form>
</p>


</body>
</html>
```






<br>

---

<br>

## index.jsp   [ROOT\bbs\index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>목록</title>

	<script language="javascript">
		function goWrite() {
			// alert("sendData");
			location.href = "write.html";
			// 미리 정의된 내장 객체
		}
	</script>

</head>
<body>

<h1>목록</h1>
<p>
<a href="javascript:goWrite()">글쓰기</a>
</p>
<%
	// 데이터베이스 연결
	Class.forName("com.mysql.jdbc.Driver");
		// 드라이버 클래스를 동적으로 로드
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
	Connection con = DriverManager.getConnection(url, "root", "mysql");

	Statement stmt = con.createStatement();
	String query = "select * from board";

	// 데이터 가져오기
	ResultSet cursor = stmt.executeQuery(query);	// ResultSet은 Content Resolver의 cursor처럼 실행됨.
	while(cursor.next()){
		int id = cursor.getInt("id");
		String title = cursor.getString("title");
		String author = cursor.getString("author");
		String content = cursor.getString("content");
		String date = cursor.getString("date");

		out.print(id + "|" + title + "|" + author + "|" + content + "|" + date + "<br/>");
	}

	con.close();

%>
</body>
</html>
```

## insert.jsp  [ROOT\bbs\insert.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="com.google.gson.*" %>

<%!
	class Data {
		String title;
		String author;
		String content;
	}
%>

<%
	// 요청값에 대한 한글처리
	request.setCharacterEncoding("utf-8");

	// json 데이터를 받아서
	String json = request.getParameter("json");

	out.println(json);

	// json 오브젝트로 변경하고
	Gson gson = new Gson();
	Data data = gson.fromJson(json, Data.class);



	// Database 연결
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
												// 데이터베이스의 주소, 유저아이디, 패스워드
	Connection con = DriverManager.getConnection(url, "root", "mysql");


	String query =
		"insert into board(title, author, content, date) value( ?, ?, ?, now())";

	PreparedStatement pstmt = con.prepareStatement(query);
	pstmt.setString(1, data.title);	// index가 1부터 시작
	pstmt.setString(2, data.author);
	pstmt.setString(3, data.content);
	pstmt.executeUpdate();		// statement 할때, stmt.execute(query);의 역할

	con.close();

%>

<html>
<head>
	<meta charset="utf-8">
</head>
<body>

<h1>
성공적으로 전송하셨습니다.
</h1>
<a href="/bbs">목록으로</a>

</body>
</html>
```

## write.html  [ROOT\bbs\write.html]
```java
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>글쓰기</title>

	<script language="javascript">
		// 함수선언 방법
		// function 함수명(파라미터, 파라미터) { }	// 인자에 타입이 들어가지 않는다.
		function sendData() {
			// 변수선언 방법
			// var 변수명;
			var title = document.getElementById("title");	// document.getElementById - 내장객체, API
			var author = document.getElementById("author");
			var content = document.getElementById("content");

			var jsonString = '{ ';
			jsonString = jsonString + '  title : "'+title.value+'"';
			jsonString = jsonString + ', author : "'+author.value+'"';
			jsonString = jsonString + ', content : "'+content.value+'"';
			jsonString = jsonString + '}'

			document.getElementById("json").value = jsonString;

			// form을 서버로 전송
			document.getElementById("form").submit();
		}
	</script>

</head>
<body>

<h1>글쓰기</h1>
<p>새글을 입력해주세요.</p>
<p>
<form name="form" id="form" method="post" action="/bbs/insert.jsp" onsubmit="return false;" />
	<input type="hidden" name="json" id="json" />
제목: <input type="text" name="title" id="title" size="30" value="" /> <br/>
작성자: <input type="text" name="author" id="author" size="30"/> <br/>
내용:  <textarea name="content" id="content" cols="30" rows="5"/></textarea> <br/>

<input type="button" onclick="sendData()" value="전송"/>

</form>
</p>

</body>
</html>
```

## insert.jsp [ROOT\bbs\json\list\index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="com.google.gson.*" %>
<%!
	// class Data {
	// 	List<Bbs> bbsList;
	// }

	class Bbs{
		int id;
		String title;
		String author;
		String content;
		String date;
	}
%>
<%
	// 데이터베이스 연결
	Class.forName("com.mysql.jdbc.Driver");
		// 드라이버 클래스를 동적으로 로드
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
	Connection con = DriverManager.getConnection(url, "root", "mysql");

	Statement stmt = con.createStatement();
	String query = "select * from board";

	// 데이터 가져오기
	ResultSet cursor = stmt.executeQuery(query);	// ResultSet은 Content Resolver의 cursor처럼 실행됨.


	// json String 만들기
	// String jsonString = " { bbsList: [ ";
	// 안에 내용
	// 수동으로 json String 변경
		// out.print("{");
		// out.print("id:" + id + ", title:\"" + title + "\", author:\"" + author + "\", content:\"" + content + ", date:" + date + "");
		// out.print("}");
	// jsonString


	List<Bbs> list = new ArrayList<>();

	while(cursor.next()){
		int id = cursor.getInt("id");
		String title = cursor.getString("title");
		String author = cursor.getString("author");
		String content = cursor.getString("content");
		String date = cursor.getString("date");

		Bbs data = new Bbs();
		data.id = id;
		data.title = title;
		data.author = author;
		data.content = content;
		data.date = date;

		list.add(data);

	}

	con.close();

	Gson gson = new Gson();
	String jsonString = gson.toJson(list);
	out.print("{\"bbsList\":" + jsonString + "}");

%>

```

<br>
---
