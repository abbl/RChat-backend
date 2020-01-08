import express from 'express';
import { buildSchema } from 'type-graphql';
import cors from 'cors';
import graphqlHTTP from 'express-graphql';
import ChatRoomResolver from './graphql/resolvers/chatroom/ChatRoomResolver';
import { schema } from './graphql/resolvers/Schema';

export default class RChatAPI {
    private expressInstance: express.Express;

    constructor(expressInstance: express.Express) {
        this.expressInstance = expressInstance;
    }

    public async start(): Promise<void> {
        this.enableCORS();
        await this.setupGraphQL();

        this.expressInstance.listen(4000);

        console.log('API enabled on port 4000');
    }

    private async setupGraphQL(): Promise<void> {
        this.expressInstance.use('/graphql', graphqlHTTP({ schema: await schema, graphiql: true }));

        console.log('GraphQL has been initialized.');
    }

    private enableCORS(): void {
        this.expressInstance.use(cors());

        console.log('Enabled cors globally.');
    }
}
