import { Field, ObjectType } from 'type-graphql';

@ObjectType()
export default class AuthenticationResult {
    @Field()
    authenticationToken: string;

    @Field()
    refreshToken: string;
}
