exports.send = function(response){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("BBS");
}