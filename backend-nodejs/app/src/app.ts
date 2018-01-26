import * as cluster from "cluster";
import * as fs from "fs";
import * as os from "os";
const AppServer = require("./server");

function start_server(id: number, config: any) {
    const port = config["server"]["port"];
    const server = new AppServer(id, config);
    server.listen(port);
    console.log(`[D] Started server on port: ${port}, thread: ${id}`);
}

function main() {
    const conf_file = process.argv[2] || "config.json";
    const config = JSON.parse(fs.readFileSync(conf_file, "utf8"));

    if (cluster.isMaster) {
        if (config["server"]["threads"] == -1) {
            config["server"]["threads"] = os.cpus().length;
        }
        if ( config["server"]["threads"] == 1 ) {
            start_server(1, config);
        } else {
            for (let i = 1; i <= config["server"]["threads"]; i++) {
                cluster.fork({"id": i});
            }
        }
    } else {
        start_server(cluster.worker.id, config);
    }
}

main();
