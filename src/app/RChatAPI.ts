import cors from 'cors';
import express from 'express';
import graphqlHTTP from 'express-graphql';
import { createConnection } from 'typeorm';
import { schema } from './graphql/resolvers/Schema';

export default class RChatAPI {
    private expressInstance: express.Express;

    constructor(expressInstance: express.Express) {
        this.expressInstance = expressInstance;
    }

    public async start(): Promise<void> {
        this.enableCORS();
        await this.setupDatabase();
        await this.setupGraphQL();

        this.expressInstance.listen(4000);

        console.log('API enabled on port 4000');
    }

    private enableCORS(): void {
        this.expressInstance.use(cors());

        console.log('Enabled cors globally.');
    }

    private async setupGraphQL(): Promise<void> {
        this.expressInstance.use('/graphql', graphqlHTTP({ schema: await schema, graphiql: true }));

        console.log('GraphQL has been initialized.');
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
}
