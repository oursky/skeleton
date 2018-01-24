import * as express from "express";
import * as session from "express-session";
import * as bodyParser from "body-parser";
import * as fs from "fs";
import * as path from "path";

class AppServer {
    private app: express.Application;

    constructor() {
        this.app = express()
                    .use(bodyParser.json())
                    ;
        // Handle bad JSON request
        this.app.use(function(err: any, req: express.Request, res: express.Response, next: any) {
            if ((err.status === 400) && ("body" in err) && (err instanceof SyntaxError)) {
                res.status(403).json({"error": "INVALID_PARAM"});
            }
        });
        // Remove useless header
        this.app.use(function (req: express.Request, res: express.Response, next: any) {
            res.removeHeader("X-Powered-By");
            next();
        });
        // Uncomment to serve static content
        // this.app.use("/", express.static(path.join(__dirname, "html")));

        // Register URI Handlers
        fs.readdirSync(path.join(__dirname, "rest"))
          .filter(file => file.endsWith(".js"))
          .forEach((module) => {
             const mod = require("./rest/" + module);
             const uri = "/rest/" + module.slice(0, -path.extname(module).length);
             switch (mod.method) {
             case "get"   : this.app.get(uri, mod.handler.bind(this)); break;
             case "post"  : this.app.post(uri, mod.handler.bind(this)); break;
             case "put"   : this.app.put(uri, mod.handler.bind(this)); break;
             case "delete": this.app.delete(uri, mod.handler.bind(this)); break;
             }
         });
    }
    listen(port: number) {
        this.app.listen(port);
    }
}

module.exports = AppServer;