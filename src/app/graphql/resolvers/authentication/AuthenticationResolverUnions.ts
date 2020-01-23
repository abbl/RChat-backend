import { createUnionType } from 'type-graphql';
import ErrorResponse from '../shared/ErrorResponse';
import AuthenticationTokens from './AuthenticationResult';

export const AuthenticationResultUnion = createUnionType({
    name: 'AuthenticationResultUnion',
    types: [AuthenticationTokens, ErrorResponse],
});
