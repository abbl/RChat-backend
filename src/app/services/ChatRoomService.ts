import { Service } from 'typedi';
import { getConnection } from 'typeorm';
import {
    CreateChatroomInput,
    DeleteChatroomInput,
    UpdateChatroomInput,
} from '../graphql/resolvers/chatroom/ChatroomInputs';
import { Chatroom } from '../models/Chatroom';
import User from '../models/User';
import ChatroomRepository from '../repositories/ChatroomRepository';

@Service()
export default class ChatroomService {
    private readonly chatRoomRepository: ChatroomRepository = getConnection().getCustomRepository(ChatroomRepository);

    public async createChatroom(createChatRoomInput: CreateChatroomInput, userEntity: User): Promise<Chatroom> {
        const chatroom: Chatroom = {
            owner: userEntity,
            users: [userEntity],
            ...createChatRoomInput,
        };

        return await this.chatRoomRepository.saveChatroom(chatroom);
    }

    public async updateChatroom(updateChatroomInput: UpdateChatroomInput, userEntity: User): Promise<Chatroom> {
        let chatroom = await this.chatRoomRepository.getChatroomById(updateChatroomInput.id);

        if (this.isAbleToModifyChatroom(chatroom, userEntity)) {
            //Checking If any field was updated.If not then method just returns chatroom object without any changes.
            if (this.chatRoomRepository.updateChatroomById(chatroom, updateChatroomInput)) {
                return await this.chatRoomRepository.getChatroomById(updateChatroomInput.id);
            }
            return chatroom;
        }
    }

    public async deleteChatroom(deleteChatroomInput: DeleteChatroomInput, userEntity: User): Promise<boolean> {
        const chatroom = await this.chatRoomRepository.getChatroomById(deleteChatroomInput.id);

        if (this.isAbleToModifyChatroom(chatroom, userEntity)) {
            return this.chatRoomRepository.removeChatroomByEntity(chatroom);
        }
    }

    private isAbleToModifyChatroom(chatroom: Chatroom, userEntity: User): boolean {
        if (chatroom) {
            if (this.isUserOwnerOfChatroom(chatroom, userEntity)) {
                return true;
            }
            throw new Error(`User isn't the owner of the chatroom.`);
        }
        throw new Error(`Chatroom doesn't exist.`);
    }

    private isUserOwnerOfChatroom(chatroom: Chatroom, userEntity: User): boolean {
        return chatroom.owner.id === userEntity.id;
    }
}
