import * as express from "express";
import  { spawn } from "child_process";

import {InvalidRequestException} from "../exceptions/InvalidRequestException.js";
import {asyncCatch} from "../middleware/ErrorHandlingMiddleware.js";
import { config } from "../config.js";


class XtextController {
    upload;

    router = express.Router();

    constructor(multipartHandler) {
        this.upload = multipartHandler;
        this.router.post('/upload', this.upload.single('xtextProject'), asyncCatch(this.saveProject));
    }

    saveProject = async (req, res, next) => {
        try {
            //TODO validate request url
            if(req.file){
                 console.log(`File '${req.file.originalname}'  received saved as  '${req.file.filename}'`);
            }

            const build = spawn('sh', ['./build.sh', req.file.filename]);

            console.log(`started build of ${req.file.filename}`)

            build.stdout.on('data', (data) => {
                console.log(`stdout: ${data}`);
            });
              
            build.stderr.on('data', (data) => {
                console.error(`stderr: ${data}`);
            });

            build.on('close', (code) => {
                console.log(`building ${req.file.filename} completed with code ${code}`);
            }); 

            res.sendStatus(200)
            
        } catch (err) {
            next(err);
        }

    }

}

export { XtextController };