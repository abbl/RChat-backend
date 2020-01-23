import { Field, InputType } from 'type-graphql';
import User from '../../../models/User';
import AuthenticationTokens from './AuthenticationResult';

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
export class RefreshAuthenticationTokenInput implements Partial<AuthenticationTokens> {
    @Field()
    refreshToken: string;
}
