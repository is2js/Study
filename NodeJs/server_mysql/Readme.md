# MySql ~ NodeJs 서버 연동
#### MySql과 NodeJs 서버를 연동해본다.
---
#### 1. MySql ~ NodeJs 서버 연동 (파일구조 및 소스코드)
#### 2. CallBack 함수에 대한 이해

<br>
<br>

# 1. MySql ~ NodeJs 서버 연동
## 파일 구조
| Depth1 | Depth2 | Depth3 | Depth4 |
| :---- | :---- | :---- | :---- |
| NodeJs(폴더) | server_mysql(폴더) | server.js |   |
|   |   | connection_module(폴더) | index.js |

<br>

## MySql ~ NodeJs 서버 연동 소스코드
#### [1] index.js (파일 경로: connection_module\index.js)

```JavaScript
var mysql = require('mysql');
var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

exports.getData = function(callback) {	// <- response 객체가 담겨온다.
	// 연결 정보를 담은 객체를 생성
	var con = mysql.createConnection(conInfo);

	// 연결 정보를 이용해서 database 연결
	con.connect();

	// 데이터베이스에 쿼리 실행
	con.query('select * from board', function(err, items, fields){
		// 에러체크
		if(err){
			console.log("error message= " + err);
		} else {
			// 데이터 저장 배열 공간을 정의
			var data = {
				bbsList : []
			};
			// 반복문을 통해 배열에 item 을 하나씩 담는다.
			items.forEach(function(item){	// 여기서는 동기로 처리
				data.bbsList.push(item);
			});
			// json String으로 변환
			var jsonString = JSON.stringify(data);
			// callback 에 넘어온 response 객체의 end 함수에 결과 데이터를 실어 보낸다.
			callback.end(jsonString);
		}
	});
	con.end()	// <- 필수! 안하면 계속 연결된 상태
}
```

<br>

#### [2] server.js (파일 경로: root)
```JavaScript
// 1. http 서버모듈 가져오기 (http안에 서버를 생성할 수 있는 함수들이 들어있다.)
var http = require("http");	// <- require는 java의 import와 같다.
var con = require("./connection_module");

// 2. http 모듈로 서버 생성하기
var server = http.createServer( function (request, response){

	console.log("요청URL:" + request.url);
	//  응답 헤더
	response.writeHead(200, {'Content-Type' : 'application/json'});
	con.getData(response);
});

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});
```

<br>

# 2. CallBack 함수에 대한 이해
## 콜백함수(CallBack)란?
- 프로그래밍에서 콜백(callback)은 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 말한다. 콜백을 넘겨받는 코드는 이 콜백을 필요에 따라 즉시 실행할 수도 있고, 아니면 나중에 실행할 수도 있다.
- 콜백함수란 어떠한 정보(또는 이벤트)를 관리하는 대상이 자신의 정보가 변경되거나 또는 이벤트가 발생할때 자신의 변경된 정보나 이벤트에 따른 어떠한 처리를 할 수 있도록 제공하는 함수라고 할 수 있다.

<br>

## CallBack 함수 동작 순서 예시
- [예시1]
![callback1](https://github.com/mdy0501/Study/blob/master/NodeJs/withMySQL/graphics/callback1.jpg)
- [예시2]
![callback2](https://github.com/mdy0501/Study/blob/master/NodeJs/withMySQL/graphics/callback2.jpg)
