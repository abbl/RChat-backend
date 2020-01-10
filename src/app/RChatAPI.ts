import { ApolloServer } from 'apollo-server-express';
import cors from 'cors';
import express from 'express';
import jwt from 'express-jwt';
import { createConnection } from 'typeorm';
import { schema } from './graphql/resolvers/Schema';

export default class RChatAPI {
    private expressInstance: express.Express;
    private apolloInstance: ApolloServer;

    constructor(expressInstance: express.Express) {
        this.expressInstance = expressInstance;
    }

    public async start(): Promise<void> {
        this.enableCORS();
        this.setupJWT();
        await this.setupDatabase();
        await this.createApolloServer();

        this.apolloInstance.applyMiddleware({ app: this.expressInstance, path: '/graphql' });

        this.expressInstance.listen(4000);

        console.log('API enabled on port 4000');
    }

    private enableCORS(): void {
        this.expressInstance.use(cors());

        console.log('Enabled cors globally.');
    }

    private async setupDatabase(): Promise<void> {
        await createConnection({
            type: 'mongodb',
            url: 'mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false',
            logging: true,
            useUnifiedTopology: true,
            entities: ['src/app/models/*.*'],
        });
    }

    private async createApolloServer(): Promise<void> {
        this.apolloInstance = new ApolloServer({
            schema: await schema,
            context: ({ req }: { req: Express.Request }) => {
                const context = {
                    ABC: 'abc',
                };

                return context;
            },
        });
    }

    private async setupJWT(): Promise<void> {
        this.expressInstance.use(
            jwt({
                secret: process.env.API_SECRET,
                credentialsRequired: false,
            }).unless({ path: ['/graphql'] }),
        );
    }
}
