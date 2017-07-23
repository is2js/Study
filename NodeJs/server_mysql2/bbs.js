var dao = require("./bbsDao");  // 현재 폴더에 있는 bbsDao를 사용한다는 뜻

exports.read = function(request, response){
    console.log("in bbs read");

    dao.select(function(){
        send(response, "READ Success!");
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
            send(response, "WRITE Success!");
        });
    });
}

exports.update = function(response){
    send(response, "UPDATE");
}

exports.delete = function(response){
    send(response, "DELETE");
}

function send(response, flag){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("BBS " + flag);
}