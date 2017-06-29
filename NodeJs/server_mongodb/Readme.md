# Server_MongoDB
#### MongoDB ~ NodeJs 서버를 연동한다.

<br>
<br>

# 서버 개요
![server_preview](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mongodb/graphics/server_preview.jpg)

<br>

# 1. MongoDB ~ NodeJs 서버 연동
## 파일 구조
| Depth1 | Depth2 | Depth3 | Depth4 |
| :---- | :---- | :---- | :---- |
| NodeJs(폴더) | server_mongodb(폴더) | [1] server.js |   |
|   |   | connection_module(폴더) | [2] index.js |
|   |   |  | [3] connection.js |

<br>

## MongoDB ~ NodeJs 서버 연동 소스코드
#### [1] server.js ( 파일 경로: root )
```JavaScript
// var 변수명 = 값
// nodejs는 파일 구조이다. class가 없다.

// 1. http 서버모듈 가져오기
var http = require("http");	// <- require는 java의 import
// http안에 서버를 생성할 수 있는 함수들이 들어있다.
// 2. 요청 url 을 분석하는 모듈 가져오기
var url = require("url");

var con = require("./connection_module");


// 2. http 모듈로 서버 생성하기
var server = http.createServer( function (request, response){

	var parsedUrl = url.parse(request.url);
	var realPath = parsedUrl.pathname;	// 실제 요청주소
	// 요청 서버 리소스 체크
	if(realPath == '/bbs/json/list'){
		// 응답 헤더
		response.writeHead(200, {'Content-Type' : 'application/json'});

		// DB처리 함수 호출
		con.getData(response);
	} else {
		response.writeHead(404, {'Content-Type' : 'text/html'});
		response.end("<h1> 404 File Not Found </h1>");
	}
});

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});
```

<br>

#### [2] index.js ( 파일 경로: connection_module\index.js )
```JavaScript
var connection = require('./connection');

// 데이터를 읽는 함수
exports.getData = function(response) {
	// connection 모듈의 select 함수 호출
	// : 함수를 호출하면서 함수 전체를 인자로 넘겨준다.
	connection.select(function(data){
		var result = {
			bbsList : []
		}
		// 원본 데이터를 클라이언트 데이터 구조에 맞게 수정한다.
		data.forEach(function(item){
			var newItem = {
				id : item.id,
				title : item.title,
				content : item.content,
				author : item.author,
				date : item.date
			};
			// 배열에 데이터 담기
			result.bbsList.push(newItem);
		});
		var jsonString = JSON.stringify(result);
		response.end(jsonString);
	});
}

exports.insert = function(data){

}

exports.update = function(data) {

}

exports.remove = function(data) {

}
```

<br>

#### [3] connection.js ( 파일 경로: connection_module\connection.js )
```JavaScript
var mongo = require("mongodb").MongoClient;

// database를 select하는 함수
exports.select = function (callback){	// <- callback에는 실행코드가 넘어온다.
	// 데이터베이스 연결
	mongo.connect('mongodb://localhost:27017/bbs', function(err, db){
		// 연결에러가 발생하면
		if(err){
			console.log(err);
		} else {
			console.log("connected: " + db);

			// 1. board 테이블의 전체데이터를 검색해서 가져온다.
			var board = db.collection("board").find();
			// 2. toArray 함수로 배열로 만들어서 docs 변수에 담아준다.
			board.toArray(function(error, docs){
				if(error){	// select에 대한 에러 체크
					console.log("error: " + error);
				} else {
					// 조회한 데이터를 result 배열에 담아준다.
					if(docs != null){
						console.log(docs);
						// callback 에는 실행가능한 코드 전체가 넘어오기 때문에
						// 넘어온 코드에 결과 데이터를 담아서 실행할 수 있다.
						callback(docs);
					}
				}
			});
			db.close();	// 처리 후 db 닫기
		}
	});
}

exports.insert = function(data, callback){
	mongo.connect('mongodb://localhost:27017/bbs', function(err,db){
		// 연결에러가 발생하면
		if(err){
			console.log(err);
		}else{
			console.log("connected:"+db);
			db.collection("board").insert(data);
			// 처리후 db 닫기
			db.close();
			//처리후 callback을 실행해 준다;
		}
	});
}
```

<br>

# 2. CallBack 함수 동작 방식
![callback_mongodb](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mongodb/graphics/callback_mongodb.jpg)
