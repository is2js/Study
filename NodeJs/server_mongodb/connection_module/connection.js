var mongo = require("mongodb").MongoClient;

// database를 select하는 함수
exports.select = function (callback){	// <- callback에는 실행코드가 넘어온다.
	// 데이터베이스 연결
	mongo.connect('mongodb://localhost:27017/bbs', function(err, db){
		// 연결에러가 발생하면 
		if(err){
			console.log(err);
		} else {
			console.log("connected: " + db);

			// 1. board 테이블의 전체데이터를 검색해서 가져온다.
			var board = db.collection("board").find().sort({"_id":-1});	// 1이면 입력순, -1이면 역순
			// 2. toArray 함수로 배열로 만들어서 docs 변수에 담아준다.
			board.toArray(function(error, docs){
				if(error){	// select에 대한 에러 체크
					console.log("error: " + error);
				} else {
					// 조회한 데이터를 result 배열에 담아준다.
					if(docs != null){
						console.log(docs);
						// callback 에는 실행가능한 코드 전체가 넘어오기 때문에
						// 넘어온 코드에 결과 데이터를 담아서 실행할 수 있다.
						callback(docs);
					}
				}
			});	
			db.close();	// 처리 후 db 닫기
		}
	});
}


exports.insert = function(data, callback){
	mongo.connect('mongodb://localhost:27017/bbs', function(err,db){
		// 연결에러가 발생하면
		if(err){
			console.log(err);
		}else{
			console.log("connected:"+db);

			/*var seq = next_seq(db, "board");
			data.id = seq;*/

			db.collection("board").insert(data);
			// 처리후 db 닫기
			db.close();
			console.log("run insert");
			//처리후 callback을 실행해 준다;
			callback();
		}
	});
}



/*function next_seq(db, table){
	// 현재 table의 count 조회
	var cursor = db.collection("seq").find({table_name : table}, {count:1, table_name:0})	// 가져오는 것만 1로 설정
	// 데이터 체크
	cursor.each(function(err, doc){
		if(err){
			var data = {
				table_name = table,
				count = 1
			}
			db.collection("seq").insert(data);
			return 1;
		} else {
			var current_count = doc.count + 1;
			// 테이블의 count 값 + 1
			var target = {table_name:table};
			var updateValue = {count : current_count};
			var option = {upsert : true};
			
			db.collection.update("seq").update(target, updateValue, option, function(err, upserted){
				if(err){
					console.log(err);
				} else {
					console.log("success!");
					return current_count;
				}
			});
		}
	});
}*/