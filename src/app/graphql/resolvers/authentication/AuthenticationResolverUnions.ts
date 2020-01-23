import { createUnionType } from 'type-graphql';
import AuthenticationTokens from '../../../authentication/AuthenticationResult';
import ErrorResponse from '../shared/ErrorResponse';

export const AuthenticationResultUnion = createUnionType({
    name: 'AuthenticationResultUnion',
    types: [AuthenticationTokens, ErrorResponse],
});
