import { Resolver, Query } from 'type-graphql';
import { ChatRoom } from '../../../models/ChatRoom';

@Resolver(ChatRoom)
export default class ChatRoomResolver {
    @Query(returns => [ChatRoom])
    public async fetchChatRooms() {
        return [];
    }
}
