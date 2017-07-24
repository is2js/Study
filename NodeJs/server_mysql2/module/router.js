var error = require("../error");
var bbs = require("../bbs");
var user = require("../user");
// request를 분석해서 요청 url에 대한 연결


// URL 을 분석
exports.parse = function(request, response){
    console.log("in router parse");
    var path = removeQuerystring(request.url);   // request.url을 하면 도메인 뒤를 가져온다.

    if(path == "/bbs"){
        parseMethod(bbs, request, response);
    }else if(path == "/user"){
        parseMethod(user, request, response);
    }else{
        error.send(response, 404);
    }
};



// http 메서드를 분석
function parseMethod(module, request, response){
    console.log("in router parseMethod");
    
    if(request.method == "POST"){
        module.write(request, response);
    }else if(request.method == "GET"){
        module.read(getQuerystring(request.url), response);
    }else if(request.method == "PUT"){
        module.update(request, response);
    }else if(request.method == "DELETE"){
        module.delete(request, response);
    }
}


// http://localhost/bbs?title=서초구
function removeQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return fullUrl;
    }else{
        return fullUrl.substring(0, position);  // 0부터 ?전까지
    }
}


function getQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return "";
    }else{
        return fullUrl.substring(position+1);  // ? 다음부터 끝까지 가져온다.
    }
}