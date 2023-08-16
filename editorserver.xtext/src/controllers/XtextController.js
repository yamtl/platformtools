import * as express from "express";

import {InvalidRequestException} from "../exceptions/InvalidRequestException.js";
import {asyncCatch} from "../middleware/ErrorHandlingMiddleware.js";
import { config } from "../config.js";


class XtextController {
    upload;

    router = express.Router();

    constructor(multipartHandler) {
        this.upload = multipartHandler;
        this.router.post('/upload', this.upload.single('file'), asyncCatch(this.saveProject));
    }

    saveProject = async (req, res, next) => {
        try {
            //TODO validate request url

            res.status(200).json(userData);
        } catch (err) {
            next(err);
        }

    }

}

export { XtextController };