# NodeJs
#### NodeJs와 관계형 데이터베이스인 Mysql을 연동해 데이터를 `insert`, `select`, `update`, `delete` (+ search) 해본다.

<br>
<br>
<br>

## 파일 구조
| Depth1 | Depth2 | Depth3 | Depth4 | Depth5 |
| :---- | :---- | :---- | :---- | :---- |
| ***NodeJs(폴더)*** | ***server_mysql2(폴더)*** | server.js |   |  |
|   |   | bbs.js  | | |
|   |   | bbsDao.js | | |
|   |   | error.js  | | |
|   |   | user.js   | | |
|   |   | ***module(폴더)***  | router.js | |
|   |   |   | ***database(폴더)*** | index.js |




<br>
<br>
<br>



## 실행순서

![NodeJs_bbsTotal](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/NodeJs_bbsTotal.png)

<br>

### callback 동작순서

![callback1](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/callback1.jpg)

![callback2](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/callback2.jpg)

<br>
<br>
<br>


## Postman 및 MySql
### 1. insert

- Postman - `POST`

![get_postman](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/get_postman.PNG)

- MySql

![post_mysql](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/post_mysql.PNG)

<br>

### 2. select

- Postman - `GET`

![post_postman](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/post_postman.PNG)

<br>

### 3. update

- Postman - `PUT`

![put_postman](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/put_postman.PNG)

- MySql

![put_mysql](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/put_mysql.PNG)

- update간 에러 메세지 발생 예시

![put_errorMessage](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/put_errorMessage.PNG)

<br>


### 4. delete

- Postman - `DELETE`

![delete_postman](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/delete_postman.PNG)

- MySql

![delete_mysql](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/delete_mysql.PNG)

<br>

### 5. search

- Postman - `GET`

![search_postman](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/search_postman.PNG)


<br>
<br>
<br>

## 소스코드
- server.js
```JavaScript
// 1. 서버모듈
var http = require("http");
var router = require("./module/router.js");

// 2. 서버를 생성
var server = http.createServer(function(request, response){
    // request <- node.js가 사용자 요청정보를 담아서 넘겨준다.
    // response <- node.js가 사용자에게 응답할 때 사용하라고 담아서 넘겨준다.

    // 요청분석
    console.log("in server");
    router.parse(request, response);

});


// 3. 서버를 시작
server.listen( 80, function() { // 80포트는 뒤에 포트이름을 생략가능하다.
    console.log("server's running...");
});
```

<br>

- router.js
```JavaScript
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


// http://localhost/bbs?title=제목 일 경우,  =>  [http://localhost/bbs] 을 반환
function removeQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return fullUrl;
    }else{
        return fullUrl.substring(0, position);  // 0부터 ?전까지
    }
}


// http://localhost/bbs?title=제목 일 경우,  =>  [title=제목] 을 반환
function getQuerystring(fullUrl){
    var position = fullUrl.indexOf('?');    // ?의 위치값을 반환. 없으면 -1 리턴
    if(position == -1){
        return "";
    }else{
        return fullUrl.substring(position+1);  // ? 다음부터 끝까지 가져온다.
    }
}
```


<br>


- bbs.js
```JavaScript
var dao = require("./bbsDao");  // 현재 폴더에 있는 bbsDao를 사용한다는 뜻
var error = require("./error");
var querystring = require("querystring");


// select, search
exports.read = function(qs, response){
    console.log("in bbs read");

    if(qs == ""){
        dao.select(function(data){  // dao를 통해 db를 읽고난 후 결과셋을 처리하는 코드
            var jsonString = JSON.stringify(data);
            send(response, "READ Success!" + jsonString);
        });
    }else{  // 검색을 위한 쿼리스트링이 있으면 쿼리스트링을 분해해서 처리한다.
        var parsedQs = querystring.parse(qs, '&', '=');

        // http://localhost/bbs?   title=제목 & author=홍길동
        // parsedQ = {
        //     title : "제목"
        //     author : "홍길동"
        // }

        dao.search(parsedQs, function(data){
            var jsonString = JSON.stringify(data);
            send(response, "Search Success!" + jsonString);
        });
    }
}

// insert
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

// update는 write(insert)와 동작방식이 유사
exports.update = function(request, response){
    console.log("in bbs update");

    // 요청한 데이터를 담을 변수를 선언
    var postData = "";

    request.on('data', function(data){  // 데이터가 버퍼에 가득차면 자동으로 호출
        postData = postData + data;
    });
    request.on('end', function(){   // 데이터를 다 읽었을 때 호출
        var dataObj = JSON.parse(postData);
        // dataObj = {
        //     "id" : 7,
        //     "title" : "수정된 제목입니다",
        //     "content" : "수정된 내용 내용 \n 내용 내용 내용",
        //     "author" : "수정된 Bewhy",
        //     "date" : "2017-07-24"
        // }
        dao.update(dataObj, function(err){
            if(err){
                error.send(response, 500, err);
            }else{
                send(response, '{"update result" : "ok"}');
            }
        });
    });
}

// delete
exports.delete = function(request, response){
    console.log("in bbs delete");

    var postData = "";
    request.on('data', function(data){
        postData = postData + data;
    });
    request.on('end', function(){
        var dataObj = JSON.parse(postData);
        dao.delete(dataObj, function(err){
            if(err){
                error.send(response, 500, err);
            }else{
                send(response, '{"delete result" : "ok"}');
            }
        });
    });
}

function send(response, result){
    response.writeHead(200, {'Content-Type':'application/json ; charset=utf-8'});
    response.end(result);
}
```

<br>

- bbsDao.js
```JavaScript
var database = require("./module/database");   //  /index.js는 생략가능
var tableName = "board";


exports.select = function(callback){
    console.log("in bbsDao select");
    var query = " select * from " + tableName;

    database.executeQuery(query, callback);
}

exports.search = function(qs, callback){
    console.log("in bbsDao search");
    var query = " select * from " + tableName + " where title like '%" + qs.title + "%' ";

    database.executeQuery(query, callback);
}

exports.insert = function(data, callback){
    console.log("in bbsDao insert");
    var query = " insert into " + tableName + " (title, content, author, date)";
        query = query + " VALUES ?";

    // var now = new Date().toLocaleDateString();
    var now = new Date().toLocaleString();

    var values = [data.title, data.content, data.author, now];
    database.executeMulti(query, values, function(){
        callback();
    });
}

exports.update = function(data, callback){
    console.log("in bbsDao update");
    var query = " update " + tableName + " set title = ?, content = ?, author = ?, date =? where id = ?";
    // var query = " update " + tableName + " set title = ? where id = ?";

    var now = new Date().toLocaleString();
    var values = [data.title, data.content, data.author, now, data.id];

    database.execute(query, values, function(error){
        callback(error);
    });
}

exports.delete = function(data, callback){
    console.log("in bbsDao delete");
    var query = " delete from " + tableName + " where id = ?";
    var values = [data.id];

    database.execute(query, values, function(){
        callback();
    });
}
```

<br>

- ./module/database/index.jx
```JavaScript
var mysql = require('mysql');

var conInfo = {
	host : '127.0.0.1',	// 데이터베이스 IP 또는 url
	user : 'root',		// 사용자 ID
	password : 'mysql',	// 비밀번호
	port : 3306,		// 포트
	database : 'bbs'	// 데이터베이스
};

// 쿼리 후에 결과값을 리턴해주는 함수 - select / search
exports.executeQuery = function(query, callback){
    var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, function(err, items, fields){
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback(items);
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수 - update / delete
exports.execute = function(query, values, callback) {
	console.log("in database excute");

	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, values, function(err, result){
		// 에러체크
		if(err){
            // 에러처리
			callback(err);
		} else {
            // 에러가 안나면
			callback();
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}

// 쿼리를 실행만 하는 함수 - insert
exports.executeMulti = function(query, values, callback) {
    console.log("in database excuteMulti");
	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, [[values]], function(err, result){
        console.log("in database excuteMulti query");
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback();
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}
```

<br>

- error.js
```JavaScript
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
```

<br>

- user.js
```JavaScript
exports.read = function(){

}

exports.wrtie = function(){

}

exports.update = function(){

}

exports.delete = function(){

}

exports.send = function(response){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("USER");
}
```




<br>
<br>
<br>

## callback에서 인자를 넘겨줄 때 예시코드
- bbs.js
```JavaScript
exports.write = function(request, response){
    console.log("in bbs write");
    // 데이터를 꺼내자.
    var postData = "";
    request.on('data', function(data){  // request.on 은 리스너이다. 데이터를 읽을 수 있을 때 호출
        postData = postData + data;
    });
    request.on('end', function(){       // 데이터를 다 읽었을 때 호출
        var dataObj = JSON.parse(postData);
        dao.insert(dataObj, function(b){
            var resultObj = {
                result : "ok",
                msg : b
            }
            send(response, JSON.stringify(resultObj));
        });
    });
}
```
- bbsDao.js
```JavaScript
exports.insert = function(data, callback){
    console.log("in bbsDao insert");
    var query = " insert into " + tableName + " (title, content, author, date)";
        query = query + " VALUES ?";
    var values = [data.title, data.content, data.author, data.date];
    database.executeMulti(query, values, function(a){
        callback(a);
    });
}
```
- index.js (database)
```JavaScript
// 쿼리를 실행만 하는 함수 - insert / delete
exports.executeMulti = function(query, values, callback) {
    console.log("in database excuteMulti");
	var con = mysql.createConnection(conInfo);  // 연결 정보를 담은 객체를 생성
	con.connect();  // 연결 정보를 이용해서 database 연결

	// 데이터베이스에 쿼리 실행
	con.query(query, [[values]], function(err, result){
        console.log("in database excuteMulti query");
		// 에러체크
		if(err){
            // 에러처리
			console.log("error message= " + err);
		} else {
            // 에러가 안나면
            callback(result);
		}
		this.end();	// mysql 연결해제 - con.end();인데 안에서 해주도록 한다. <- 필수! 안하면 계속 연결된 상태
	});
}
```

### 결과화면

![callbackResult](https://github.com/mdy0501/Study/blob/master/NodeJs/server_mysql2/graphics/callbackResult.PNG)
