import { ApolloServer } from 'apollo-server-express';
import cors from 'cors';
import express from 'express';
import jwt from 'express-jwt';
import figlet from 'figlet';
import Container, { Inject } from 'typedi';
import { createConnection } from 'typeorm';
import { schema } from './graphql/resolvers/Schema';
import UserService from './services/UserService';
import { JWTRequest } from './types/JWTRequest';

export default class RChat {
    private expressInstance: express.Express;
    private apolloInstance: ApolloServer;

    @Inject()
    private userService: UserService;

    constructor(expressInstance: express.Express) {
        this.expressInstance = expressInstance;
    }

    public async start(): Promise<void> {
        this.displayLogo();
        this.enableCORS();
        this.setupJWT();
        await this.setupDatabase();
        await this.createApolloServer();
        this.userService = Container.get(UserService);
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
            type: 'postgres',
            host: 'localhost',
            port: 5432,
            username: 'postgres',
            password: '123',
            database: 'rchat',
            synchronize: true,
            entities: ['src/app/models/*.*'],
        });
    }

    private async createApolloServer(): Promise<void> {
        this.apolloInstance = new ApolloServer({
            schema: await schema,
            context: async ({ req }: { req: JWTRequest }) => {
                return {
                    userEntity: req.user ? await this.userService.findOneById(req.user.id) : undefined,
                };
            },
        });
    }

    private async setupJWT(): Promise<void> {
        this.expressInstance.use(
            jwt({
                secret: process.env.API_SECRET,
                credentialsRequired: false,
            }),
        );
    }

    private displayLogo(): void {
        const logo = figlet.textSync('----- \n RChat \n----- ');

        console.log(logo);
    }
}
