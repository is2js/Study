# NodeJs
#### NodeJs와 관계형 데이터베이스인 Mysql을 연동해 데이터를 `insert`, `select`, `update`, `delete` 해본다.

<br>
<br>

## 파일 구조
| Depth1 | Depth2 | Depth3 | Depth4 | Depth5 |
| :---- | :---- | :---- | :---- | :---- |
| ***NodeJs(폴더)*** | ***server_mysql2(폴더)*** | server.js |   |  |
|   |   | bbs.js  | | |
|   |   | bbsDao.js | | |
|   |   | error.js  | | |
|   |   | user.js   | | |
|   |   | ***module(폴더)***  | router.js | |
|   |   |   | connection.js | |
|   |   |   | ***database(폴더)*** | index.js |


<br>
<br>

## 서버 생성
```JavaScript
// 1. 서버모듈
var http = require("http");

// 2. 서버를 생성


var server = http.createServer(function(request, response){ // 콜백함수 <- 사용자 요청이 있을시에 호출
    // request <- node.js가 사용자 요청정보를 담아서 넘겨준다.
    // response <- node.js가 사용자에게 응답할 때 사용하라고 담아서 넘겨준다.

});



// 3. 서버를 시작
server.listen( 80, function() { // 콜백함수 <- start 후에 호출 : 단순 참고용 로그
    console.log("server's running...");
});


// 요청분석
function requestParser(request, response){
    console.log(request.url);
    if(request.url == "/hello"){
        sendResponse(response);
    } else {
        send404(response);
    }
}


// 응답처리
function sendResponse(response){
    response.writeHead(200, {'Content-Type' : 'text/html'});
    for(i=0; i<10; i++){
        response.write( i + "<br/>");
    }
    response.write("Hello");
    response.end();    // response.end("Hello");
    // 한줄만 쓸 경우에는 response.end("Hello"); 를 쓰기도 한다.
}

// 오류처리
function send404(response){
    response.writeHead(404, {'Content-Type' : 'text/html'});
    response.end("404 Page Not Found");
}
```


<br>
<br>

---


## 실행순서
- server.js
```JavaScript
// 1. 서버모듈
var http = require("http");
var router = require("./module/router.js");

// 2. 서버를 생성
var server = http.createServer(function(request, response){
    // request <- node.js가 사용자 요청정보를 담아서 넘겨준다.
    // response <- node.js가 사용자에게 응답할 때 사용하라고 담아서 넘겨준다.

    // 요청분석
    console.log("in server");
    router.parse(request, response);

});


// 3. 서버를 시작
server.listen( 80, function() { // 80포트는 뒤에 포트이름을 생략가능하다.
    console.log("server's running...");
});
```

<br>

- router.js
```JavaScript
var error = require("../error");
var bbs = require("../bbs");
var user = require("../user");
// request를 분석해서 요청 url에 대한 연결


// URL 을 분석
exports.parse = function(request, response){
    console.log("in router parse");
    var path = splitQuerystring(request.url);   // request.url을 하면 도메인 뒤를 가져온다.

    if(path == "/bbs"){
        parseMethod(bbs, request, response);
    }else if(path == "/user"){
        parseMethod(user, request, response);
    }else{
        error.send(response, 404);
    }
};



// http 메서드를 분석
function parseMethod(module, request, response){
    console.log("in router parseMethod");

    if(request.method == "POST"){
        module.write(request, response);
    }else if(request.method == "GET"){
        module.read(response);
    }else if(request.method == "PUT"){
        module.update(response);
    }else if(request.method == "DELETE"){
        module.delete(response);
    }
}


// http://localhost/bbs?title=서초구
function splitQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return fullUrl;
    }else{
        return fullUrl.subString(0, position);  // 0부터 ?전까지
    }
}
```

<br>

- bbs.js
```JavaScript
var dao = require("./bbsDao");  // 현재 폴더에 있는 bbsDao를 사용한다는 뜻

exports.read = function(response){
    send(response, "READ");
}

exports.write = function(request, response){
    console.log("in bbs write");
    // 데이터를 꺼내자.
    var postData = "";
    request.on('data', function(data){  // request.on 은 리스너이다. 데이터를 읽을 수 있을 때 호출
        postData = postData + data;
    });
    request.on('end', function(){       // 데이터를 다 읽었을 때 호출
        var dataObj = JSON.parse(postData);
        dao.insert(dataObj, function(){
            send(response, "WRITE Success!");
        });
    });
}

exports.update = function(response){
    send(response, "UPDATE");
}

exports.delete = function(response){
    send(response, "DELETE");
}

function send(response, flag){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("BBS " + flag);
}
```

<br>

- bbsDao.js
```JavaScript
var database = require("./module/database");   // /index.js는 생략가능
var tableName = "board";


exports.select = function(){
    var query = "select * from " + tableName + " ";
}

exports.insert = function(data, callback){
    console.log("in bbsDao insert");
    var query = " insert into " + tableName + "(title, content, author, date)";
        query = query + " VALUES ?";
    var values = [
        [data.title, data.content, data.author, data.date]        
    ];
    database.executeMulti(query, values, function(){
        callback();
    });
}

exports.update = function(){
    var query = "update " + tableName + " ";
}

exports.delete = function(){
    var query = "delete from " + tableName + " ";
}
```

<br>

- ./module/database/index.jx
```JavaScript
var mysql = require('mysql');

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

// 쿼리 후에 결과값을 리턴해주는 함수
exports.executeQuery = function(query, callback){
    var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, function(err, items, fields){
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback(items);
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수
exports.execute = function(query, callback) {	// <- response 객체가 담겨온다.
	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, function(err, result){
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback();
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수
exports.executeMulti = function(query, values, callback) {
    console.log("in database excuteMulti");
	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, [values], function(err, result){
        console.log("in database excuteMulti query");
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback(result);
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}
```

<br>

- error.js
```JavaScript
exports.send = function(response, code){
    response.writeHead(code, {'Content-Type':'text/html'});

    // 에러코드 분석
    if(code == 404){
        response.end(code + " Page Not Found");
    }else if(code == 500){
        response.end(code + " Internal Server Error");
    }
}
```

<br>

- user.js
```JavaScript
exports.read = function(){

}

exports.wrtie = function(){

}

exports.update = function(){

}

exports.delete = function(){

}

exports.send = function(response){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("USER");
}
```

<br>
<br>


## Postman - BODY
- insert.jsp
```json
{
	"title" : "제목입니다",
	"content" : "내용 내용 \n 내용 내용 내용",
	"author" : "Bewhy",
	"date" : "2017-07-23"
}
```

<br>

- delete.jsp
```json
{
	"id" : "5"
}
```

<br>

- update.jsp
```json
{
	"id" : "7",
	"title" : "수정된 제목입니다",
	"content" : "수정된 내용 내용 \n 내용 내용 내용",
	"author" : "수정된 Bewhy",
	"date" : "2017-07-24"
}
```
