let date = new Date();

if(date.getHours() < 12) {
	console.log("오전입니다.");
}

if(12 <= date.getHours()){
	console.log("오후입니다.");
}