exports.read = function(response){
    send(response, "READ");
}

exports.write = function(response){
    send(response, "WRITE");
}

exports.update = function(response){
    send(response, "UPDATE");
}

exports.delete = function(response){
    send(response, "DELETE");
}

function send(response, flag){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("BBS " + flag);
}