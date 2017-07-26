var database = require("../module/database");   //  /index.js는 생략가능
var tableName = "board";


exports.select = function(callback){
    console.log("in bbsDao select");
    var query = " select * from " +tableName+ " order by id desc"; // 반대는 asc

    database.executeQuery(query, callback);
}

exports.search = function(qs, callback){
    console.log("in bbsDao search");
    var query = " select * from " + tableName + " where title like ? ";
    var values = ["%"+qs.title+"%"];

    database.executeQueryValues(query, values, callback);
}

exports.insert = function(data, callback){
    console.log("in bbsDao insert");
    var query = " insert into " + tableName + " (title, content, author, date)";
        query = query + " values(?,?,?,?)";

    // var now = new Date().toLocaleDateString();
    var now = new Date().toLocaleString();

    var values = [data.title, data.content, data.author, now];
    database.execute(query, values, function(){
        callback();
    });
}

exports.update = function(data, callback){
    console.log("in bbsDao update");
    var query = " update " + tableName + " set title = ?, content = ?, author = ?, date =? where id = ?";
    // var query = " update " + tableName + " set title = ? where id = ?";
    
    var now = new Date().toLocaleString();
    var values = [data.title, data.content, data.author, now, data.id];
    
    database.execute(query, values, function(error){
        callback(error);
    });
}

exports.delete = function(data, callback){
    console.log("in bbsDao delete");
    var query = " delete from " + tableName + " where id = ?";
    var values = [data.id];

    database.execute(query, values, function(){
        callback();
    });
}