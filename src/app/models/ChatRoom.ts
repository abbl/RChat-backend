import { Entity, Column, ObjectIdColumn, ObjectID } from 'typeorm';
import { ObjectType, ID, Field } from 'type-graphql';

/**
 * Model representing ChatRoom entity.
 */
@Entity()
@ObjectType()
export class ChatRoom {
    @ObjectIdColumn()
    @Field(type => ID)
    _id: ObjectID;

    @Column()
    @Field()
    name: string;

    @Column()
    @Field()
    description: string;

    @Column()
    @Field()
    owner: string;

    @Column()
    @Field()
    isPrivate: boolean;
}
