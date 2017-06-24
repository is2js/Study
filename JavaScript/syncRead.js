// 모듈을 추출
const fs = require('fs');

// 파일을 읽어 들이고 출력
const file = fs.readFileSync('textfile.txt');
console.log(file);
console.log(file.toString());