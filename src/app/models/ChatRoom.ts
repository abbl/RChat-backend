import { Field, ID, ObjectType } from 'type-graphql';
import { Column, Entity, JoinColumn, ManyToOne, ObjectID, PrimaryGeneratedColumn } from 'typeorm';
import User from './User';

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

    @ManyToOne(type => User)
    @JoinColumn()
    @Field()
    owner: User;

    @Column()
    @Field()
    isPrivate: boolean;
}
