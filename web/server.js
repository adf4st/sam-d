/**
 * Created by alexfabian
 */

var express  = require('express');
var app      = express(); 								// create our app w/ express

var webPort  = process.env.WEB_PORT || 8888; 				// set the port

var api_host = process.env.API_HOST || 'localhost';
var api_port = process.env.API_PORT || '8080';
var api_context_root = process.env.API_CONTEXT_ROOT || '/api';

app.use(express.static(__dirname + '/public')); 				// set the static files location /public/img will be /img for users

app.get('/', function(req, res) {
    res.sendfile('./public/index.html');
});

var env = {
    API_HOST : api_host,
    API_PORT : api_port,
    API_CONTEXT_ROOT : api_context_root,
    API_BASE_URL: 'http://' + api_host + ":" + api_port + api_context_root
};

app.get('/env', function(req, res) {
   res.send(env);
});

app.listen(webPort);
console.log("App listening on port " + webPort);