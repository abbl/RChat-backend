import { Arg, Authorized, Ctx, Query, Resolver } from 'type-graphql';
import { Inject } from 'typedi';
import { ContextStructure } from '../../../authentication/ContextStructure';
import { ROLES } from '../../../constants/Roles';
import { Chatroom } from '../../../models/Chatroom';
import ChatroomDTO from '../../../models/DTO/ChatroomDTO';
import ChatroomService from '../../../services/ChatroomService';
import { CreateChatroomInput, DeleteChatroomInput, UpdateChatroomInput } from './ChatroomInputs';

@Resolver(Chatroom)
export default class ChatroomResolver {
    @Inject()
    private chatroomService: ChatroomService;

    @Authorized(ROLES.user)
    @Query(returns => ChatroomDTO)
    public async createChatroom(
        @Arg('data') createChatroomInput: CreateChatroomInput,
        @Ctx() ctx: ContextStructure,
    ): Promise<ChatroomDTO> {
        const createdChatroom = await this.chatroomService.createChatroom(createChatroomInput, ctx.userEntity);

        return new ChatroomDTO(createdChatroom);
    }

    @Authorized(ROLES.user)
    @Query(returns => ChatroomDTO)
    public async updateChatroom(
        @Arg('data') updateChatroomInput: UpdateChatroomInput,
        @Ctx() ctx: ContextStructure,
    ): Promise<ChatroomDTO> {
        const updatedChatroom = await this.chatroomService.updateChatroom(updateChatroomInput, ctx.userEntity);

        return new ChatroomDTO(updatedChatroom);
    }

    @Authorized(ROLES.user)
    @Query(returns => Boolean)
    public async deleteChatroom(
        @Arg('data') deleteChatroomInput: DeleteChatroomInput,
        @Ctx() ctx: ContextStructure,
    ): Promise<Boolean> {
        const removedChatroom = await this.chatroomService.deleteChatroom(deleteChatroomInput, ctx.userEntity);

        return removedChatroom;
    }
}
