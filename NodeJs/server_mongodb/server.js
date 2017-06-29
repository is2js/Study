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

////// 여기까지가 tomcat을 만든 것이다.