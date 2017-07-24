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





/*

// 요청분석
function requestParser(request, response){
    console.log(request.url);
    router.parse(request);
}



// 응답처리
function sendResponse(response){
    response.writeHead(200, {'Content-Type' : 'text/html'});
    for(i=0; i<10; i++){
        response.write( i + "<br/>");
    }
    response.write("Hello");
    response.end();
    // response.end("Hello");
}

// 오류처리
function send404(response){
    response.writeHead(404, {'Content-Type' : 'text/html'});
    response.end("404 Page Not Found");
}


*/