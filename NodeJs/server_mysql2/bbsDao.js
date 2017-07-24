var database = require("./module/database");   // /index.js는 생략가능
var tableName = "board";


exports.select = function(callback){
    console.log("in bbsDao select");
    var query = " select * from " + tableName;

    database.executeQuery(query, callback);
}

exports.insert = function(data, callback){
    console.log("in bbsDao insert");
    var query = " insert into " + tableName + " (title, content, author, date)";
        query = query + " VALUES ?";
    var values = [data.title, data.content, data.author, data.date];
    database.executeMulti(query, values, function(){
        callback();
    });
}

exports.update = function(data, callback){
    console.log("in bbsDao update");
    var query = " update " + tableName + " set title = ?, content = ?, author = ?, date =? where id = ?";
    // var query = " update " + tableName + " set title = ? where id = ?";
    var values = 
        // ['data.title', 'data.content', 'data.author', 'data.date', 'data.id'];
        // ["data.title", "data.id"];
        ["data.title", "data.content", "data.author", "data.date", "data.id"];
        // [data.title], [data.content], [data.author], [data.date], [data.id]

    database.executeUpdate(query, values, function(){
        callback();
    });
}

exports.delete = function(data, callback){
    console.log("in bbsDao delete");
    var query = " delete from " + tableName + " where id = ?";
    var values = [data.id];

    database.executeMulti(query, values, function(){
        callback();
    });
}

function createDate(response){
    var d = new Date();

    var localeDate = d.toLocaleDateString();
    var localeTime = d.toLocaleTimeString();
}

function send(response, flag){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("BBS " + flag);
}