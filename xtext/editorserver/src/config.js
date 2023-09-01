

export const config = {

    port: process.env.PORT || "10001",

    endpointsPrefix: process.env.TS_ENDPOINT_PREFIX || "",

    trustedWebOrigins: ['http://127.0.0.1:8080'],


}