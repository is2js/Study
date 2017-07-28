// 서버모듈
var http = require("http");
var mysql = require("mysql");
var url = require("url");
var querystring = require("querystring");

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'mydb'	// 데이터베이스
};

// 1-1. 서버를 생성
var server = http.createServer(function(request, response){
    // request : 사용자 요청정보 조회
    // response : 사용자에게 처리결과 응답


    // 1. 요청 url 분석 처리
    // /airbnb/house?checkin=2017-07-27&checkout=2017-07-31&a=1&b=abc

    if(request.url.startsWith("/airbnb/house")){
        var parsedUrl = url.parse(request.url);

        // 쿼리만 뽑아내서 쿼리를 search 객체로 만들어준다.
        var search = querystring.parse(parsedUrl.query);

        // 가. 검색조건이 없는 검색
//        executeQuery(response);
        // 나. 검색조건이 있는 검색
        
        executeQuery(response, search);
    }else{
        response.writeHead(404, {"Content-Type":"text/html"});
        response.end("404 page not found!");
    }

});



// 2. 쿼리 실행
function executeQuery(response, search){
    var query = " select * from house as a join reservation as b "
                + " on a.id = b.house_id ";
    var values = [];

    console.log("\n\n");
    console.log(search);
    console.log("\n\n");


    if(search){
        query = query + " where 1=1 ";
        // search 오브젝트의 key를 반복문을 돌면서 꺼내고

        for(key in search){
            // key 이름을 쿼리에 삽입하고
            query = query + " and b." + key + " = ? ";
            // key로 조회한 값을 valuse에 담는다.
            values.push(search[key]);
        }
    }
    console.log(values);

    // select * from house where checkin = ? and checkout = ?
    console.log("QUERY : " + query);

    


    var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
    con.connect();  // 연결 정보를 이용해서 database 연결

    // 데이터베이스에 쿼리 실행
    con.query(query, values, function(err, items, fields){

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



// 3. 결과값 전송
function sendResult(response, data){
    response.writeHead(200, {"Content-Type":"text/html"});
    response.end(JSON.stringify(data));
}



// 1-2. 서버를 시작
server.listen( 80, function() { // 80포트는 뒤에 포트이름을 생략가능하다.
    console.log("server's running...");
});