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


obj.bbsList.push(item1);
obj.bbsList.push(item2);

obj.bbsList.pop(item2);

console.log(obj);


var jsonString = JSON.stringify(obj);
console.log(jsonString);