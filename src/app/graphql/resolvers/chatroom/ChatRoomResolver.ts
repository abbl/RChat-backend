import { Query, Resolver } from 'type-graphql';
import { ChatRoom } from '../../../models/ChatRoom';

@Resolver(ChatRoom)
export default class ChatRoomResolver {
    @Query(returns => [ChatRoom])
    public async fetchChatRooms() {
        return [];
    }

    public async createChatRoom() {}

    public async updateChatRoom() {}

    public async deleteChatRoom() {}
}
