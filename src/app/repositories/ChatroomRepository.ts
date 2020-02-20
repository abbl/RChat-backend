import { EntityRepository, Repository } from 'typeorm';
import { Chatroom } from '../models/Chatroom';

@EntityRepository(Chatroom)
export default class ChatroomRepository extends Repository<Chatroom> {
    /**
     * Saves the chatroom and checks If it's name isn't already taken
     * by someone else.
     * @param chatroom
     */
    public async saveChatroom(chatroom: Chatroom): Promise<Chatroom> {
        if (this.isChatRoomNameAvailable(chatroom.name)) {
            return this.save(chatroom);
        }
        throw new Error(`Name of the chatroom is already taken.`);
    }

    /**
     * Updates chatroom by id and returns true if any column was updated.
     */
    public async updateChatroomById<T extends Partial<Chatroom>>(
        chatroom: Chatroom,
        partialEntity: T,
    ): Promise<boolean> {
        if (partialEntity.name) {
            if (!this.isChatRoomNameAvailable(partialEntity.name)) {
                throw new Error(`Name of the chatroom is already taken.`);
            }
        }
        const updateResult = await this.update(chatroom.id, partialEntity);

        return updateResult.affected > 0;
    }

    public async removeChatroomByEntity(chatroom: Chatroom): Promise<boolean> {
        return Boolean(this.remove(chatroom));
    }

    public async getChatroomById(id: string): Promise<Chatroom> {
        return this.findOne(id);
    }

    public async getChatroomByName(name: string): Promise<Chatroom> {
        return await this.findOne({ where: { name: name } });
    }

    private async isChatRoomNameAvailable(name: string): Promise<boolean> {
        return !this.getChatroomByName(name);
    }
}
