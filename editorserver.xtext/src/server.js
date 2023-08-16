
import express from 'express';
import cors from 'cors';
import multer from 'multer';

import * as http from 'http';

import {config} from "./config.js";
import {XtextController} from './controllers/XtextController.js';
import {errorHandlingMiddleware} from "./middleware/ErrorHandlingMiddleware.js";


const expressApp = express();
const upload = multer({ dest: "uploads/" });

expressApp.use('*', express.json());

var corsOptions = {
  origin: config.trustedWebOrigins,
  optionsSuccessStatus: 200 // some legacy browsers (IE11, various SmartTVs) choke on 204
}

expressApp.use(cors(corsOptions));


const controllers = { 
  '/xtext': new XtextController(upload)
};


for (const [path, controller] of Object.entries(controllers)) {
  expressApp.use(config.endpointsPrefix + path, controller.router)
}

expressApp.use(errorHandlingMiddleware);


expressApp.listen(config.port, function() {
  console.log(`Auth server is listening on HTTP port ${config.port}`)
})

