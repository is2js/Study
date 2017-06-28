# UrlModule
#### URL Module에 대해서 알아본다.

<br>
<br>

## URL 모듈이란?
- url 모듈은 url 정보를 객체로 가져와 분석하거나(parse) url 객체를 문자열로 바꿔주는 기능(format, resolve)을 수행한다.

- URL
  - url.parse(urlStr, [parseQueryString], [slashesDenoteHost])
  - url.format(urlObj)
  - url.resolve(from, to)

<br>

## url.parse(urlStr, [parseQueryString], [slashesDenoteHost])
- url 문자열(urlStr)을 url 객체로 변환하여 리턴한다.
- parseQueryString과 slashesDenoteHost는 기본값으로 false이다.

#### parseQueryString
- true : url 객체의 query 속성을 객체 형식으로 가져온다.(querystring 모듈을 사용)
- false : url 객체의 query 속성을 문자열 형식으로 가져온다.

#### slashesDenoteHost
- true : urlStr이 '//foo/bar' 인 경우 foo는 host, /bar는 path로 인식한다.
- false : urlStr이 '//foo/bar' 인 경우 //foo/bar 전체를 path로 인식하고 host는 null이다.

<br>

## URL 모듈 예제
#### 예제
```JavaScript
var url = require('url');
var parsedObject = url.parse('http://user:pass@gmail.com:8080/p/a/t/h?query=string#hash');

console.log(parsedObject);              // url 객체 정보 출력
console.log(url.format(parsedObject));  // url 객체를 문자열로 출력
```
#### 실행결과
```JavaScript
{ protocol: 'http:',
  slashes: true,
  auth: 'user:pass',
  host: 'gmail.com:8080',
  port: '8080',
  hostname: 'host.com',
  hash: '#hash',
  search: '?query=string',
  query: 'query=string',
  pathname: '/p/a/t/h',
  path: '/p/a/t/h?query=string',
  href: 'http://user:pass@gmail.com:8080/p/a/t/h?query=string#hash' }

http://user:pass@gmail.com:8080/p/a/t/h?query=string#hash
```


<br>

## 예제1
- #### server.js 파일
```JavaScript
// require는 java의 import 와 같다.
// http안에 서버를 생성할 수 있는 함수들이 들어있다.
var http = require("http");	// 1. http 서버모듈 가져오기
var url = require("url");	// 2. 요청 url 을 분석하는 모듈 가져오기

// 3. http 모듈로 서버 생성하기
var server = http.createServer( function (request, response){

	console.log("===========================");
	console.log("요청 URL: " + request.url);
	console.log("===========================");
	// 문자열로 된 요청 url을 객체로 만들어서 사용하게 해준다.
	var parsedUrl = url.parse(request.url);
	console.log(parsedUrl);
	console.log("===========================");
	console.log("객체화된 URL: " + parsedUrl);
	console.log("===========================");
	console.log("주소 분리: " + parsedUrl.pathname);
	console.log("===========================");
	console.log("query 분리: " + parsedUrl.query);
	console.log("===========E N D===========");

	// 응답 헤더
	response.writeHead(200, {'content-Type' : 'application/json'});
} );

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});
```

<br>

## 예제2 (요청 서버 리소스 체크)
- #### server.js 파일
```JavaScript
var http = require("http");	// 1. http 서버모듈 가져오기
var url = require("url");	// 2. 요청 url 을 분석하는 모듈 가져오기

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
		response.writeHead(404, {'Content-Type' : 'application/json'});
		response.end("<h1> 404 File Not Found </h1>");
	}
});

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});
```

- #### connection_module.js 파일

```JavaScript
var mysql = require('mysql');

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

exports.getData = function() {
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
			console.log(jsonString);
		}
	});
	con.end()	// <- 필수! 안하면 계속 연결된 상태
}
```
