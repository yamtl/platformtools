
export class InvalidRequestException extends Error {
    statusCode = 400
    code = 'invalid_request'
    logInfo = ''
    
    constructor() {
        super("Invalid request")
    }
}