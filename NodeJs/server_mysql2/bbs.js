var dao = require("./bbsDao");  // 현재 폴더에 있는 bbsDao를 사용한다는 뜻
var error = require("./error");
var querystring = require("querystring");


// select, search
exports.read = function(qs, response){
    console.log("in bbs read");

    if(qs == ""){
        dao.select(function(data){  // dao를 통해 db를 읽고난 후 결과셋을 처리하는 코드
            var jsonString = JSON.stringify(data);
            send(response, "READ Success!" + jsonString);
        });
    }else{  // 검색을 위한 쿼리스트링이 있으면 쿼리스트링을 분해해서 처리한다.
        var parsedQs = querystring.parse(qs, '&', '=');

        // http://localhost/bbs?   title=제목 & author=홍길동
        // parsedQ = {
        //     title : "제목"
        //     author : "홍길동"
        // }

        dao.search(parsedQs, function(data){
            var jsonString = JSON.stringify(data);
            send(response, "Search Success!" + jsonString);
        });
    }
}

// insert
exports.write = function(request, response){
    console.log("in bbs write");
    // 데이터를 꺼내자.
    var postData = "";
    request.on('data', function(data){  // request.on 은 리스너이다. 데이터를 읽을 수 있을 때 호출
        postData = postData + data;
    });
    request.on('end', function(){       // 데이터를 다 읽었을 때 호출
        var dataObj = JSON.parse(postData);
        dao.insert(dataObj, function(){
            send(response, '{"write result" : "ok"}');
        });
    });
}

// update는 write(insert)와 동작방식이 유사
exports.update = function(request, response){
    console.log("in bbs update");

    // 요청한 데이터를 담을 변수를 선언
    var postData = "";
    
    request.on('data', function(data){  // 데이터가 버퍼에 가득차면 자동으로 호출
        postData = postData + data;
    });
    request.on('end', function(){   // 데이터를 다 읽었을 때 호출
        var dataObj = JSON.parse(postData);
        // dataObj = {
        //     "id" : 7,
        //     "title" : "수정된 제목입니다",
        //     "content" : "수정된 내용 내용 \n 내용 내용 내용",
        //     "author" : "수정된 Bewhy",
        //     "date" : "2017-07-24"
        // }
        dao.update(dataObj, function(err){
            if(err){
                error.send(response, 500, err);
            }else{
                send(response, '{"update result" : "ok"}');
            }
        });
    });
}

// delete
exports.delete = function(request, response){
    console.log("in bbs delete");

    var postData = "";
    request.on('data', function(data){
        postData = postData + data;
    });
    request.on('end', function(){
        var dataObj = JSON.parse(postData);
        dao.delete(dataObj, function(err){
            if(err){
                error.send(response, 500, err);
            }else{
                send(response, '{"delete result" : "ok"}');
            }
        });
    });
}

function send(response, result){
    response.writeHead(200, {'Content-Type':'application/json ; charset=utf-8'});
    response.end(result);
}