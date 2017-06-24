// 모듈을 추출
const fs = require('fs');

// 파일을 읽음
fs.readFile('textfile.txt', (error, file) => {
	// 출력
	console.log(file);
	console.log(file.toString());
});