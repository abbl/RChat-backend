import express from 'express';

export default class RChatAPI {
    private expressInstance: express.Express;

    constructor(expressInstance: express.Express) {
        this.expressInstance = expressInstance;
    }
}
