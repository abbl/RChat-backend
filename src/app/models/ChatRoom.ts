import { Entity, Column, ObjectIdColumn, ObjectID } from 'typeorm';

@Entity()
export class ChatRoom {
    @ObjectIdColumn()
    _id: ObjectID;

    @Column()
    name: string;

    @Column()
    description: string;

    @Column()
    owner: string;

    @Column()
    isPrivate: boolean;
}
