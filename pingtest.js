var net = require('net');

var start;

var client = net.connect(8089, function() {
	var buf = new Buffer(32);
	buf.writeInt32BE(4, 0);
	buf.write("PING", 4, 4);
	buf.writeInt32BE(0, 8);

	start = Date.now();

	client.write(buf);
});

client.on('data', function(buf) {
	var end = Date.now();
	var ms = end - start;
	console.log('Start:', start);
	console.log('End:', end);
	console.log('Full circle time:', ms, 'ms');

	var statusCode = buf.readInt32BE(0);
	console.log('Status ', statusCode);
	
	var paramCount = buf.readInt32BE(4);
	var offsetBase = 8; 
	for(var i = 0 ; i < paramCount ; i++) {
		var strLength = buf.readInt32BE(offsetBase + (i * 4));
		var strStart = offsetBase + (i * 4) + 4;
		var strEnd = strStart + strLength;
		var str = buf.toString('utf8', strStart, strEnd);

		console.log('Parameter ', i + 1);
		console.log(str);
	}
});