const AppServer = require("./server");

const s = new AppServer();
s.listen(8080);

export = s;