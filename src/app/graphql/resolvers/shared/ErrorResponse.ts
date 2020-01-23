import { Field, ObjectType } from 'type-graphql';

@ObjectType()
export default class ErrorResponse {
    @Field()
    code: number;

    @Field()
    message: string;
}
