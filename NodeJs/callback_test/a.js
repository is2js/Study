var bFile = require("./b");
var cFile = require("./c");
var value = 15;

console.log( bFile.next(value) );
bFile.prev(value);

bFile.print("today", function(param){
    console.log(param);
});


// 비동기 함수를 호출할 때는 결과처리 코드가 호출측에 있어야 한다.
cFile.readText('write.txt', function(param){
    console.log(param);
}); // write.txt 에서 읽은 결과를 출력하세요.


cFile.writeText("파일명.txt", "파일내용", function(error){
    if(error){
        // 에러처리
    }else{
        // 완료처리
    }
});