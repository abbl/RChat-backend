import { Field, ObjectType } from 'type-graphql';
import { Column, Entity, ObjectID, ObjectIdColumn } from 'typeorm';

@ObjectType()
@Entity()
export default class User {
    @ObjectIdColumn()
    _id?: ObjectID;

    @Column()
    @Field()
    username: string;

    @Column()
    @Field()
    password: string;

    @Column()
    @Field()
    email: string;

    @Column()
    @Field()
    role: string;
}
