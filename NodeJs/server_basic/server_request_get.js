var http = require('http');

// 1. 요청한 url을 객체로 만들기 위해 url 모듈 사용
var url = require('url');

// 2. 요청한 url 중에 Query String을 객체로 만들기 위해 querystring 모듈 사용
var querystring = require('querystring');

var server = http.createServer( function(request, response) {
	// 3. 콘솔 화면에 로그 시작 부분을 출력
	console.log('--- log start ---');
	// 4. 브라우저에서 요청한 주소를 parsing 하여 객체화한 후 출력
	var parseUrl = url.parse(request.url);
	console.log(parsedUrl);

})