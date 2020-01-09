import { Field, InputType } from 'type-graphql';
import User from '../../../models/User';

@InputType()
export class SignInInput implements Partial<User> {
    @Field()
    username: string;

    @Field()
    password: string;
}

@InputType()
export class SignUpInput implements Partial<User> {
    @Field()
    username: string;

    @Field()
    password: string;

    @Field()
    email: string;
}

@InputType()
export class RenewTokenInput {
    @Field()
    token: string;
}
