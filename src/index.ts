import express from 'express';
import 'reflect-metadata';
import RChat from './app/RChat';

const app = express();

const instance = new RChat(app);

instance.start();
