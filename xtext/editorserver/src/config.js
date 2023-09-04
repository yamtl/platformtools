

export const config = {

    port: process.env.ES_PORT || "10000",

    endpointsPrefix: process.env.ES_ENDPOINT_PREFIX || "",

    trustedWebOrigins: ['http://127.0.0.1:8080'],


}