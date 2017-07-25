exports.send = function(response, code, err){
    response.writeHead(code, {'Content-Type':'application/json ; charset=utf-8'});

    // 에러코드 분석
    if(code == 404){
        var errorObj = {
            result : "404 Page Not Found",
            msg : ""
        }
        response.end(JSON.stringify(errorObj));
    }else if(code == 500){
        var errorObj = {
            result : "500 Internal Server Error",
            msg : err
        };
        response.end(JSON.stringify(errorObj));
    }
}