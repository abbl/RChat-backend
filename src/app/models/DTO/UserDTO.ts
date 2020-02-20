import { Field, ObjectType } from 'type-graphql';
import User from '../User';

@ObjectType()
export class UserDTO implements Partial<User> {
    @Field()
    public username: string;

    @Field()
    public role: string;

    constructor(user: User) {
        this.username = user.username;
        this.role = user.role;
    }
}
