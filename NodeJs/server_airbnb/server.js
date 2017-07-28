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
    var query = "select * from house";
    if(search){
        query = " select * from house where id not in "
              + " (select a.id "
              + "   from house a join reservation b "
              + "     on a.id = b.house_id "
              + "  where b.checkin between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or b.checkout between '"+search.checkin+"' and '"+search.checkout+"' "
              + "     or (b.checkin <= '"+search.checkin+"' and b.checkout >= '"+search.checkout+"') "
              + " ) ";
        if(search.guests > -1){
            query = query + " and guests > " + search.guests;
        }
        if(search.type > -1){
            query = query + " and type = " + search.type;
        }
        if(search.price_min > -1 && search.price_max > -1){
            query = query + " and price between " + search.price_min + " and " + search.price_max;
        }
        if(search.amenities > -1){ // wifi 여부만 체크
            query = query + " and amenities = " + search.amenities;
        }
        console.log("Query:"+query);
    }
    var con = mysql.createConnection(conInfo);
    con.connect();
    con.query(query, function(err, items, fields){ 
        if(err){
            console.log(err);
            sendResult(response, err);
        }else{
            console.log(items);
            sendResult(response, items);
        }
        this.end();
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