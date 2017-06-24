// 모듈을 추출
const url = require('url');

// 모듈을 사용
const parsedObject =
	url.parse('http://www.hanbit.co.kr/store/books/look.php?p_code=B4250257160');

console.log(parsedObject);