// 1. 서버모듈
var http = require("http");
var mysql = require("mysql");

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'mydb'	// 데이터베이스
};

// 2. 서버를 생성
var server = http.createServer(function(request, response){
    // request : 사용자 요청정보 조회
    // response : 사용자에게 처리결과 응답
    if(request.url.startsWith("/airbnb")){
        executeQuery(response);
    }else{
        response.writeHead(404, {"Content-Type":"text/html"});
        response.end("404 page not found!");
    }

});

// 3. 서버를 시작
server.listen( 80, function() { // 80포트는 뒤에 포트이름을 생략가능하다.
    console.log("server's running...");
});


function executeQuery(response){
    var query = " select * from house where id = ?";
        var values = [1];

        var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
        con.connect();  // 연결 정보를 이용해서 database 연결

        // 데이터베이스에 쿼리 실행
        con.query(query, values, function(err, items, fields){
            console.log("in database executeQueryValues query");

            // 에러체크
            if(err){
                // 에러처리
                console.log("error message= " + err);
                sendResult(response, err);
            } else {
                // 에러가 안나면
                console.log(items);
                sendResult(response, items);
            }
            this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
        });
}

function sendResult(response, data){
    response.writeHead(200, {"Content-Type":"text/html"});
    response.end(JSON.stringify(data));
}