import { createUnionType } from 'type-graphql';
import AuthenticationResult from '../../../authentication/AuthenticationResult';
import ErrorResponse from '../shared/ErrorResponse';

export const AuthenticationResultUnion = createUnionType({
    name: 'AuthenticationResultUnion',
    types: [AuthenticationResult, ErrorResponse],
});
