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




	/*items.forEach(function(item){
		console.log(item);
	});

	for(Object item : items){

	}*/