import * as express from "express";
import  { spawn } from "child_process";
import  fs from "fs";

import {asyncCatch} from "../middleware/ErrorHandlingMiddleware.js";
import { config } from "../config.js";


class XtextController {
    upload;

    router = express.Router();

    constructor(multipartHandler) {
        this.upload = multipartHandler;
        this.router.post('/upload', this.upload.single('xtextProject'), asyncCatch(this.saveProject));
        this.router.get('/editors/:editorId/status', asyncCatch(this.editorStatus));
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

            let response = {};
            response.editorUrl= `${config.deployAddress}/${req.file.filename}/`;
            response.editorStatusUrl= `${config.address}/xtext/editors/${req.file.filename}/status`

            res.status(200).json(response);
            
        } catch (err) {
            next(err);
        }

    }

    editorStatus = async (req, res, next) => {
        try {
            const editorId = req.params.editorId;
            const filePath = config.deployFileLocation + "/" + editorId;
            const editorDeployed = fs.existsSync(filePath);
            res.status(200).json({editorReady: editorDeployed});

        } catch (err) {
            next(err);
        }
    }
        

}

export { XtextController };