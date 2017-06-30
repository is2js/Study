var connection = require('./connection');

// 데이터를 읽는 함수
exports.getData = function(response) {
	// connection 모듈의 select 함수 호출
	// : 함수를 호출하면서 함수 전체를 인자로 넘겨준다.
	connection.select(function(data){
		var result = {
			bbsList : []
		}
		// 원본 데이터를 클라이언트 데이터 구조에 맞게 수정한다.
		data.forEach(function(item){
			var newItem = {
				id : item.id, 
				title : item.title, 
				content : item.content,
				author : item.author,
				date : item.date
			};
			// 배열에 데이터 담기
			result.bbsList.push(newItem);
		});
		var jsonString = JSON.stringify(result);
		
		// 3. 데이터를 전송 - Head부분의 메타 정보를 보내준다.
		response.writeHead(200, {'Content-Type' : 'application/json'});
		
		/* 네트워크처리 순서 */
		// 1. 연결
		// 2. 스트림을 열고
		// 3. 데이터를 전송
		// 4. 연결을 닫는다.

		// end는 3번과 4번을 해주는 것이다.
		response.end(jsonString);
	});
}

// 데이터를 저장하는 함수
exports.setData = function(data, response){
	var obj = JSON.parse(data);
	console.log(obj);

	connection.insert(obj, function(){
		console.log("call insert callback");
		response.writeHead(200, {'Content-Type' : 'application/json'});
		response.end('{"result_status" : "ok"}');
	});
}

exports.update = function(data) {

}

exports.remove = function(data) {

}