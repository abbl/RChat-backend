import { buildSchema } from 'type-graphql';
import Container from 'typedi';
import { graphQLAuthenticationChecker } from '../../authentication/GraphQLAuthenticationChecker';
import AuthenticationResolver from './authentication/AuthenticationResolver';
import ChatroomResolver from './chatroom/ChatroomResolver';
import UserResolver from './user/UserResolver';

export const schema = buildSchema({
    resolvers: [ChatroomResolver, UserResolver, AuthenticationResolver],
    container: Container,
    authChecker: graphQLAuthenticationChecker,
});
