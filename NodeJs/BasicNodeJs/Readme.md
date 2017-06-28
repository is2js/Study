# NodeJs
#### 본격적인 NodeJs 학습에 앞서 기본적인 것들을 학습해본다.

<br>

## Java , JavaScript , JSON 비교
- Java
```java
class obj {
	String aaa = "value123";
	int bbb = 123;

	public void ccc() {
		Log.i("", "called");
	}
}
```

- Javascript
```javascript
var obj = {
	aaa : "value123",
	bbb : 123,
	ccc : function(){ console.log('called'); }
};
```

- JSON
```java
var json = '{ aaa : "value123", bbb : 123 }'
```

<br>

## 객체와 함수 비교

(1) 함수 (이름을 소문자로 시작)
```JavaScript
  function obj() {
    String aaa = "value123";
    int bbb = 123;
  }
```

(2) 객체 (이름을 대문자로 시작)
```JavaScript
  function Obj() {
    String aaa = "value123";
    int bbb = 123;
  }
```

<br>

### JavaScript Object
```JavaScript
// obj 라는 이름으로 배열을 담는 저장소를 저장한다.
var obj = {
	bbsList : []
};

var item1 = {
	no : 1,
	title : "제목",
	content : "내용입니다",
	author : "홍길동",
	date : "2017-06-28 11:33:30"
};

var item2 = {
	no : 2,
	title : "제목2",
	content : "내용입니다2",
	author : "이순신",
	date : "2017-06-28 11:35:12"
};

// obj 객체의 bbsList 변수의 배열에 아이템을 하나씩 담는다.
obj.bbsList.push(item1);
obj.bbsList.push(item2);

// obj 객체의 bbsList 변수의 배열에 아이템을 하나씩 빼낸다.
obj.bbsList.pop(item2);

console.log(obj);
```


### Json Object
```JavaScript
// 완성된 obj 객체를 json 스트링으로 변경한다.
var jsonString = JSON.stringify(obj);
console.log(jsonString);
```

<br>

## 동기방식 vs 비동기방식 비교
#### 1. 동기 (Synchronous : 동시에 일어나는)
- 동기는 말 그대로 동시에 일어난다는 뜻이다.
	- 요청과 결과가 한 자리에서 동시에 일어난다.
	- A노드와 B노드 사이의 작업 처리 단위(transaction)를 동시에 맞추겠다.

#### 2. 비동기 (Asynchronous : 동시에 일어나지 않는)
- 비동기는 동시에 일어나지 않는다는 뜻이다.
	- 요청한 그 자리에서 결과가 주어지지 않는다.
	- 노드 사이의 작업 처리 단위(transaction)를 동시에 맞추지 않아도 된다.

#### 3. 동기와 비동기의 장/단점
##### (1) 동기방식
- 장점
	- 설계가 매우 간단하고 직관적이다.
- 단점
	- 결과가 주어질 때까지 아무것도 못하고 대기해야 한다.

##### (2) 비동기방식
- 장점
 	- 결과가 주어지는데 시간이 걸리더라도 그 시간 동안 다른 작업을 할 수 있으므로 자원을 효율적으로 사용할 수 있다.
- 단점
	- 동기보다 복잡하다.

#### 4. 예시
##### (1) 동기 방식
```JavaScript
  var result = con.query('select * from board'){

  };
```
##### (2) 비동기 방식 (비동기방식으로 하기 위해 **CallBack** 함수 사용)
```JavaScript
  con.query('select * from board', function(){

  });
```

<br>

## connection.js
```JavaScript
var mysql = require('mysql');

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

// 연결 정보를 담은 객체를 생성
var con = mysql.createConnection(conInfo);

// 연결 정보를 이용해서 database 연결
con.connect();

// 데이터베이스에 쿼리 실행
con.query('select * from board', function(err, items, fields){
	// 에러체크
	if(err){
		console.log("error message= " + err);
	} else {
		// 데이터 저장 배열 공간을 정의
		var data = {
			bbsList : []
		};

		// 반복문을 통해 배열에 item 을 하나씩 담는다.
		items.forEach(function(item){	// 여기서는 동기로 처리
			data.bbsList.push(item);
		});
		// json String으로 변환
		var jsonString = JSON.stringify(data);
		console.log(jsonString);
	}

});

con.end()	// <- 필수! 안하면 계속 연결된 상태

```

## connection.js에서 item.forEach 비교
- JavaScipt 코드
```JavaScript
	items.forEach(function(item){
		console.log(item);
	});
```
- Java 코드일 경우
```java
	for(Object item : items){

	}
```

<br>

## 파일을 모듈화 하기

#### 변수 , 함수

(1) 변수
```JavaScript
var var1 = "Hello ";

// 접근제한자 public을 하는 것과 같음.
exports.var2 = 3.1415;
```

(2) 함수
```JavaScript
function printString (str){
  console.log(var1 + str);
}

// 접근제한자 public을 하는 것과 같음.
exports.printString = function (str){
	console.log(val1 + str);  
}
```

**<사용하기>**
```JavaScript
var custom = require("./custom_module");

custom.printString("James");

console.log(custom.var2);
```


#### 모듈만들기 예제
- custom_module.js
```JavaScript
var var1 = "Hello ";

exports.printString = function (str){
	console.log(var1 + str);
}
```

- home.js
```JavaScript
var custom = require("./custom_module");

custom.printString("James");
```
