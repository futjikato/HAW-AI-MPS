var net = require('net'),
	readline = require('readline');

var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

function userInput(cb) {
	rl.question("Action ('quit' to quit):", function(actionName) {

		if(actionName == "quit") {
			cb(false, []);
			return;
		}

		var params = [];

		function readParam() {
			rl.question("Parameter " + (params.length + 1) + " ('done' to finish):", function(paramStr) {
				if(paramStr == "done") {
					rl.pause();
					cb(actionName, params);
				} else {
					params.push(paramStr);
					readParam();
				}
			});
		}
		readParam();
	});
}

function loop(client) {
	var buf = new Buffer(512);

	function writeStr(val, offset) {
		buf.writeInt32BE(val.length, offset);
		buf.write(val, offset + 4, val.length);

		return offset + 4 + val.length;
	}

	userInput(function(action, params) {
		if(!action) {
			return;
		}

		var offset = writeStr(action, 0);

        buf.writeInt32BE(9999, offset);
        offset += 4;

		buf.writeInt32BE(params.length, offset);
		offset += 4;

		params.forEach(function(paramStr) {
			offset = writeStr(paramStr, offset);
		});

		var sendBuffer = buf.slice(0, offset);
		client.write(sendBuffer);
		readOnce(client, function() {
			rl.resume();
			loop(client);
		});
	});
}

function readOnce(client, cb) {
	client.once('data', function(buf) {
        var offset = 0;

		var statusCode = buf.readInt32BE(offset);
		console.log('Status ', statusCode);
        offset += 4;

        var responseNameLength = buf.readInt32BE(offset);
        offset += 4;
        var responseName = buf.toString('utf8', offset, offset + responseNameLength);
        console.log('response name', responseName);
        offset += responseNameLength;

        var userCode = buf.readInt32BE(offset);
        console.log('UserID ', userCode);
        offset += 4;
		
		var paramCount = buf.readInt32BE(offset);
        offset += 4;

		var offsetBase = offset;
		for(var i = 0 ; i < paramCount ; i++) {
			var strLength = buf.readInt32BE(offsetBase + (i * 4));
			var strStart = offsetBase + (i * 4) + 4;
			var strEnd = strStart + strLength;
			var str = buf.toString('utf8', strStart, strEnd);

			console.log('Parameter ', i + 1);
			console.log(str);
		}

		cb();
	});
}

var client = net.connect(8089, function() {
	loop(client);
});