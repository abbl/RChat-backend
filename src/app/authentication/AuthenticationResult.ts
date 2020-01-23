import { Field, ObjectType } from 'type-graphql';

@ObjectType()
export default class AuthenticationTokens {
    @Field()
    authenticationToken: string;

    @Field()
    refreshToken: string;
}
