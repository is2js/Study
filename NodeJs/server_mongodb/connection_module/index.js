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
		response.end(jsonString);
	});
}

exports.insert = function(data){

}

exports.update = function(data) {

}

exports.remove = function(data) {

}