import * as express from "express";

module.exports = (function() {
/**
 * @api {post} /login Authenicate User
 * @apiName login
 * @apiGroup auth
 * @apiDescription Authenicate user.
 *
 * @apiParam {String} email Registered email
 * @apiParam {String} pass Password
 * @apiExample {json} Example
 *     {
 *       "email": "foo@bar.com",
 *       "pass": "1234"
 *     }
 *
 * @apiSuccessExample {json} SUCCESS
 *     HTTP/1.1 200 OK
 *     {
 *       "session": "token"
 *     }
 *
 * @apiError INVALID_PARAM Invalid parameter
 * @apiErrorExample {json} INVALID_PARAM
 *     HTTP/1.1 403 Forbidden
 *     {
 *       "error": "INVALID_PARAM"
 *     }
 * @apiError UNAUTHORIZED Unauthorized
 * @apiErrorExample {json} UNAUTHORIZED
 *     HTTP/1.1 403 Unauthorized
 *     {
 *       "error": "UNAUTHORIZED"
 *     }
 */
function handler(req: express.Request, res: express.Response) {
    if (!("email" in req.body) || !("pass" in req.body)) {
        res.status(403).json({"error": "INVALID_PARAM"});
        return;
    }
    res.json({
        "session": "token"
    });
}
// EXPORTS
// -----------------------------------------------------------------
return {
    method: "post",
    handler: handler
};
}());
