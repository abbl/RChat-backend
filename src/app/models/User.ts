import { Field, ObjectType } from 'type-graphql';
import { Column, Entity, ManyToMany, PrimaryGeneratedColumn } from 'typeorm';
import { Chatroom } from './Chatroom';

@ObjectType()
@Entity()
export default class User {
    @PrimaryGeneratedColumn()
    id?: string;

    @Column()
    @Field()
    username: string;

    @Column()
    password: string;

    @Column()
    @Field()
    email: string;

    @Column()
    @Field()
    role: string;

    @ManyToMany(type => Chatroom, { cascade: true })
    chatrooms?: Chatroom[];
}
