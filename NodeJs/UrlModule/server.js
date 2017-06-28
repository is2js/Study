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