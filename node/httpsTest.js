console.log("Hello World");

const http = require('http');
const https = require('https');
const fs = require('fs');

var options = {
	host: 'encrypted.google.com',
	port: 443,
	path: "/#q=test",
    method: 'GET'
}

https.request(options, function(res) {
    console.log('STATUS: ' + res.statusCode);
    console.log('HEADERS: ' + JSON.stringify(res.headers));
    res.setEncoding('utf8');
    res.on('data', function (chunk) {
        console.log('BODY: ' + chunk);
	console.log('test')
    });
}).end();
