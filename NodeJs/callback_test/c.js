var fs = require("fs"); // fs모듈: 파일을 읽고 쓰기 위한 모듈


// 비동기로 파일읽기
// fs.readFile('파일명', '텍스트일 경우 엔코딩', callback(error, data){  });
exports.readText = function(fileName, callback){
    fs.readFile(fileName, 'utf-8', function(error, data){
        // 결과처리할 어떤 것이 호출되어야만 한다.
        callback(data);
    });
}


// 비동기로 파일쓰기
// fs.writeFile('파일명', '쓸내용', 'utf-8', function(error){  });
var content = "첫번째 내용입니다." + "두번째 내용입니다." + "세번째 내용입니다.";
fs.writeFile('write.txt', content, 'utf-8', function(error){
    if(error){
        console.log(error);
    }else{
        console.log("쓰기완료");
    }
});


exports.writeText = function(fileName, data, callback){
    fs.writeFile(fileName, data, 'utf-8', function(error){
        if(error){
            callback(error);
        }else{
            // 자동으로 overload가 된다.
            callback();
        }
    });
}