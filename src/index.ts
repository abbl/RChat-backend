import 'reflect-metadata';
import express from 'express';
import RChatAPI from './app/RChatAPI';

const app = express();

const instance = new RChatAPI(app);

instance.start();
