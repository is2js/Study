// 입력된 parameter에 1을 더해서 리턴
exports.next = function(param){
    return param+1;
}

exports.prev = function(param){
    console.log(param-1);
}

exports.print = function(param, callback){
    var result = "[Result : " + param + " ]";
    callback(result);
}