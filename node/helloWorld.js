console.log("Hello World");

const http = require('http');

var options = {
    host: 'localhost',
    port: 9080,
    path: '/movies',
    method: 'GET'
}

http.request(options, function(res) {
    console.log('STATUS: ' + res.statusCode);
    console.log('HEADERS: ' + JSON.stringify(res.headers));
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
        console.log('BODY: ' + chunk);
	console.log('test')
    });
}).end();
