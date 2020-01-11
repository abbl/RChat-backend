import { Field, ID, ObjectType } from 'type-graphql';
import { Column, Entity, ObjectID, PrimaryGeneratedColumn } from 'typeorm';

/**
 * Model representing ChatRoom entity.
 */
@Entity()
@ObjectType()
export class ChatRoom {
    @PrimaryGeneratedColumn()
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
