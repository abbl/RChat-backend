import { buildSchema } from 'type-graphql';
import Container from 'typedi';
import AuthenticationResolver from './authentication/AuthenticationResolver';
import ChatRoomResolver from './chatroom/ChatRoomResolver';
import UserResolver from './user/UserResolver';

export const schema = buildSchema({
    resolvers: [ChatRoomResolver, UserResolver, AuthenticationResolver],
    container: Container,
});
