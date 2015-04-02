var http = require('http');
http.createServer(function (req, res) {
       var body = '';
        req.on('data', function (data) {
            body += data;
        });
        req.on('end', function () {
            console.log(body);

            var dgram = require('dgram');
            var toSend;
            var client = dgram.createSocket("udp4");

            var action = JSON.parse(body);
            console.log(action);
            var command = action.Cue;
            if(command === 'Next')
            {
                console.log('Cue next!');
                toSend = 'Cue Go';
            }
            else if(command === 'Previous')
            {
                console.log('Cue Previous!');
                toSend = 'Cue Back';
            }
            else
            {
                console.log('Cue Number!');
                toSend = 'Cue ' + command + ' Go';
            }
            client.send(toSend, 0, toSend.length, 65000, 
                "localhost", function(err) {
                client.close();
            });
        });
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end();
}).listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337/');
