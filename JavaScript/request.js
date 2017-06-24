// 모듈을 추가
const request = require('request');

// request 모듈을 사용
const url = 'http://www.hanbit.co.kr/store/books/new_book_list.html';
request(url, (error, response, body) => {
	console.log(body);
});