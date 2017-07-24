var dao = require("./bbsDao");  // 현재 폴더에 있는 bbsDao를 사용한다는 뜻

exports.read = function(request, response){
    console.log("in bbs read");

    dao.select(function(data){  // dao를 통해 db를 읽고난 후 결과셋을 처리하는 코드
        var jsonString = JSON.stringify(data);
        send(response, "READ Success!" + jsonString);
    });
}

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

exports.update = function(request, response){
    console.log("in bbs update");

    var postData = "";
    request.on('data', function(data){
        postData = postData + data;
    });
    request.on('end', function(){
        var dataObj = JSON.parse(postData);
        dao.update(dataObj, function(){
            send(response, '{"update result" : "ok"}');
        });
    });
}

exports.delete = function(request, response){
    console.log("in bbs delete");

    var postData = "";
    request.on('data', function(data){
        postData = postData + data;
    });
    request.on('end', function(){
        var dataObj = JSON.parse(postData);
        dao.delete(dataObj, function(){
            send(response, '{"delete result" : "ok"}');
        });
    });
}

function send(response, result){
    response.writeHead(200, {'Content-Type':'application/json'});
    response.end(result);
}