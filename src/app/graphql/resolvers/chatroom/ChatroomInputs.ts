import { Length } from 'class-validator';
import { Field, InputType } from 'type-graphql';
import { Chatroom } from '../../../models/Chatroom';

@InputType()
export class CreateChatroomInput implements Partial<Chatroom> {
    @Field()
    @Length(3, 32)
    name: string;

    @Field()
    @Length(0, 255)
    description: string;

    @Field()
    isPrivate: boolean;
}

@InputType()
export class UpdateChatroomInput implements Partial<Chatroom> {
    @Field()
    id: string;

    @Field({ nullable: true })
    @Length(3, 32)
    name?: string;

    @Field({ nullable: true })
    @Length(0, 255)
    description?: string;

    @Field({ nullable: true })
    isPrivate?: boolean;
}

@InputType()
export class DeleteChatroomInput implements Partial<Chatroom> {
    @Field()
    id: string;
}
