# Callback함수 에 대한 이해
- #### callback 함수에 대해서 알아본다.
- #### 비동기로 `파일읽기(fs.readFile)`, `파일쓰기(fs.writeFile)` 를 해본다.

<br>
<br>
<br>

## 파일 입출력

#### fs 모듈 불러오기
```JavaScript
var fs = require("fs"); // fs모듈: 파일을 읽고 쓰기 위한 모듈
```

#### 1. 비동기로 파일읽기
```JavaScript
fs.readFile('파일명', '텍스트일 경우 엔코딩', callback(error, data){  });
```

#### 2. 비동기로 파일쓰기
```JavaScript
fs.writeFile('파일명', '쓸내용', 'utf-8', function(error){  });
```

<br>
<br>

## 소스 코드
#### a.js
  - ##### 비동기 함수를 호출할 때는 결과처리 코드가 호출측에 있어야 한다.
```JavaScript
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
```

#### b.js
```JavaScript
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
```

### c.js

```JavaScript
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
```

<br>
<br>

## 결과화면
```
16
14
[Result : today ]
첫번째 내용입니다.두번째 내용입니다.세번째 내용입니다.
쓰기완료
```
