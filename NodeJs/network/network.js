var express = require('express');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

// express 객체 생성
var app = express();

var http = require('http');


// express 함수에 middleware나 router를 등록하는 방법
app.use(cookieParser());
app.use(bodyParser.json());	

app.use(function(req, res, next){
	console.log(req.body.name);
	res.cookie( 'user', {'id' : 'bewhy', 'pw' : '1234'});
	res.end();
});


var server = http.createServer(app);

server.listen(7337, function(){
	console.log('server is running...')
})



// on 노드에서 어떤 이벤트가 발생할 때