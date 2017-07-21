var tableName = "bbs";

exports.select = function(){
    var query = "select * from " + tableName + " ";
}

exports.insert = function(){
    var query = "insert into " + tableName + " ";
}

exports.update = function(){
    var query = "update " + tableName + " ";
}

exports.delete = function(){
    var query = "delete from " + tableName + " ";
}