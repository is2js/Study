var conn = require("./connection_module");


function printResult(result){
	console.log(result);
}

var result = conn.getData(printResult);

