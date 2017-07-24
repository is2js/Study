var mysql = require('mysql');

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

// 쿼리 후에 결과값을 리턴해주는 함수 - select
exports.executeQuery = function(query, callback){
    var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, function(err, items, fields){
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback(items);
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수 - update / delete
exports.execute = function(query, values, callback) {
	console.log("in database excute");

	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, values, function(err, result){
		// 에러체크
		if(err){
            // 에러처리
			callback(err);
		} else {
            // 에러가 안나면
			callback();
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수 - insert
exports.executeMulti = function(query, values, callback) {
    console.log("in database excuteMulti");
	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, [[values]], function(err, result){
        console.log("in database excuteMulti query");
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback();
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}