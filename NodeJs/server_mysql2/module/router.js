var error = require("../error");
var bbs = require("../bbs");
var user = require("../user");
// request를 분석해서 요청 url에 대한 연결


// URL 을 분석
exports.parse = function(request, response){
    var path = splitQuerystring(request.url);

    if(path == "/bbs"){
        bbs.send(response);
    }else if(path == "/user"){
        user.send(response);
    }else{
        error.send(response, 404);
    }
};



// http 메서드를 분석
function parseMethod(request){
    
    if(request.method == "POST"){

    }else if(request.method == "GET"){

    }else if(request.method == "PUT"){

    }else if(request.method == "DELETE"){

    }
}


// http://localhost/bbs?title=서초구
function splitQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return fullUrl;
    }else{
        return fullUrl.subString(0, position);  // 0부터 ?전까지
    }
    
}