import { Authorized, Query, Resolver } from 'type-graphql';
import { ROLES } from '../../../constants/Roles';
import { ChatRoom } from '../../../models/ChatRoom';

@Resolver(ChatRoom)
export default class ChatRoomResolver {
    @Query(returns => [ChatRoom])
    @Authorized(ROLES.admin)
    public async fetchChatRooms() {
        return [];
    }

    public async createChatRoom() {}

    public async updateChatRoom() {}

    public async deleteChatRoom() {}
}
