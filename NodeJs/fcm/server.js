// ------------ FCM 설정 ------------
// 전송할 FCM 주소
var fcmUrl = "https://fcm.googleapis.com/fcm/send";

// 내 서버키
var serverKey = "AAAA2-jHyKI:APA91bETMaWMHqxIdkFaCK0__u6gkvGQdNmGLIqm4MJZwkNSfGNHe70-Ch7jHrfY6YAyXI09_OowGogP6l35tVj78tEq12ybtfbLD2wt3lagpfXJYn-e6PXCfO8UXfnEqObBt1QmaCPo";
       
var message = {
	to : "상대방 토큰",
	notification : {
		title : "FireBaseFCM",
		body : "노티바에 나타나는 메시지"
	}
};

// ------------ NodeJs ------------
// 1. http 서버모듈 가져오기
var http = require("http");
var url = require("url");
var httpUrlConnection = require("request");
var querystring = require("querystring");

// 2. http 모듈로 서버 생성하기
var server = http.createServer( function (request, response){
	// 4. 사용자 요처이 발생하면 요청 자원을 분리하고
	var parsedUrl = url.parse(request.url);
	var path = parsedUrl.pathname;

	// 5. 사용자 요청이 발생하면 요청 자원을 분석해서 send_notification을 체크
	if(path == "/send_notification"){
		if(request.method == "POST"){
			// 6. 스마트폰에서 전송된 데이터를 message 객체에 담고
			var postData = "";
			request.on('data', function(data){
				postData = postData + data;
			});
			request.on('end', function(){
				console.log("new postData:"+postData);
				
				var dataObj = JSON.parse(postData);

				message.to = dataObj.token;
				message.notification.body = dataObj.msg;

				console.log(message);

				// 7. httpUrlConnection 으로 FCM 서버로 전송
				httpUrlConnection({
					// fcm 서버로 데이터 전송
					url : fcmUrl,
					method : "POST",
					headers : {
						"Authorization" : "key="+serverKey,
						"Content-Type" : "application/json"
					},
					body : JSON.stringify(message)

				}, function(error, res, body){
					// fcm 서버에 결과처리 상태를 반환
					if(!error && res.statusCode == 200){
						// 8. 결과처리 상태 스마트폰으로 전송
						response.writeHead(200, {'Content-Type' : 'application/json'});
						response.end('{"result_status" : "'+ body + '"}');
					} else {
						response.writeHead(501, {'Content-Type' : 'application/json'});
						response.end('{"result_status" : "'+ body + '"}');
					}
				});
			});
			
		} else {
			send404(response);
		}
	} else {
		send404(response);
	}
});

function send404(response){
	response.writeHead(404, {'Content-Type' : 'application/json'});
	response.end('{"result_status" : "404 Page Not Found"}');
}

// 3. 서버가 로드되면 알려주고, 사용자 요청 대기하기
server.listen(8080, function(){
	console.log("server is running...");
});