exports.read = function(){
    
}

exports.wrtie = function(){

}

exports.update = function(){

}

exports.delete = function(){

}

exports.send = function(response){
    response.writeHead(200, {'Content-Type':'text/html'});
    response.end("USER");
}