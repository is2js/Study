//모듈을 추출
const fs = require('fs');

// 파일을 씀
fs.writeFileSync('output.txt', '안녕하세요!');
console.log('파일 쓰기를 완료 했습니다.');