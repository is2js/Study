# HttpBbs2
### Statement / PreparedStatement 사용

<br>

## [insert.jsp]
#### (1) Statement 사용
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%
	String title = request.getParameter("title");
	String author = request.getParameter("author");
	String content = request.getParameter("content");

	// Database 연결
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
												// 데이터베이스의 주소, 유저아이디, 패스워드
	Connection con = DriverManager.getConnection(url, "root", "mysql");

	Statement stmt = con.createStatement();
	String query =
		"insert into board(title, author, content, date) value('"+title+"', '"+author+"', '"+content+"', now())";
	stmt.execute(query);	// execute는 sqlite의 query와 같음.

	con.close();

%>

<h1>
성공적으로 전송하셨습니다.
</h1>
```

#### (2) PreparedStatement 사용
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>
<%
	String title = request.getParameter("title");
	String author = request.getParameter("author");
	String content = request.getParameter("content");

	// Database 연결
	Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://127.0.0.1:3306/bbs";
												// 데이터베이스의 주소, 유저아이디, 패스워드
	Connection con = DriverManager.getConnection(url, "root", "mysql");


	String query =
		"insert into board(title, author, content, date) value( ?, ?, ?, now())";

	PreparedStatement pstmt = con.prepareStatement(query);
	pstmt.setString(1, title);	// index가 1부터 시작
	pstmt.setString(2, author);
	pstmt.setString(3, content);
	pstmt.executeUpdate();		// statement 할때, stmt.execute(query);의 역할

	con.close();
%>

<h1>
성공적으로 전송하셨습니다.
</h1>
<a href="/bbs">목록으로</a>
```


## [index.jsp]
```java
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page language="java" import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>목록</title>
</head>
<body>

<h1>목록</h1>
<p>
<a href="write.html">글쓰기</a>
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

## [write.html]
```html
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"></meta>
	<title>글쓰기</title>
</head>
<body>

<h1>글쓰기</h1>
<p>새글을 입력해주세요.</p>
<p>
<form method="get" action="/bbs/insert.jsp">
제목: <input type="text" name="title" size="30"> <br/>
작성자: <input type="text" name="author" size="30"> <br/>
내용:  <textarea name="content" cols="30" rows="5"></textarea> <br/>
<input type="submit" value="전송"/>
</form>
<p>
</body>
</html>
```
