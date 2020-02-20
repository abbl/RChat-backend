import { Field, ObjectType } from 'type-graphql';
import { Chatroom } from '../Chatroom';
import { UserDTO } from './UserDTO';

@ObjectType()
export default class ChatroomDTO implements Partial<Chatroom> {
    @Field()
    public name: string;

    @Field()
    public description: string;

    @Field()
    public isPrivate: boolean;

    @Field()
    public ownedBy: UserDTO;

    constructor(chatroom: Chatroom) {
        this.name = chatroom.name;
        this.description = chatroom.description;
        this.isPrivate = chatroom.isPrivate;
        this.ownedBy = new UserDTO(chatroom.owner);
    }
}
