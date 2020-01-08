import { buildSchema } from 'type-graphql';
import ChatRoomResolver from './chatroom/ChatRoomResolver';

export const schema = buildSchema({
    resolvers: [ChatRoomResolver],
});
