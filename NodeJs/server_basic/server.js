// var 변수명 = 값
// nodejs는 파일 구조이다. class가 없다.

// 1. http 서버모듈 가져오기
var http = require("http");	// <- require는 java의 import
// http안에 서버를 생성할 수 있는 함수들이 들어있다.



// 2. 요청 url 을 분석하는 모듈 가져오기
var url = require("url");


// 콜백함수는 이름 없이 사용할 수 있다. (무조건 하나만 호출하는 것이니까 이름이 굳이 필요가 없다.)
// 2. http 모듈로 서버 생성하기
var server = http.createServer( function (request, response){

	console.log("요청 URL: " + request.url);
	// 문자열로 된 요청 url을 객체로 만들어서 사용하게 해준다.
	var parsedUrl = url.parse(request.url);
	console.log(parsedUrl);
	console.log("객체화된 URL: " + parsedUrl);
	console.log("주소 분리: " + parsedUrl.pathname);
	console.log("query 분리: " + parsedUrl.query);

	// 응답 헤더
	response.writeHead(200, {'content-Type' : 'application/json'});
	// 응답 데이터
	var one = '{bbsList:[{id:"1", title:"제목", content:"내용", author:"작성자", date:"2012/12/12"}]}';
	// 응답 데이터 전송 후 완료
	response.end(one);

} );

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});

////// 여기까지가 tomcat을 만든 것이다.