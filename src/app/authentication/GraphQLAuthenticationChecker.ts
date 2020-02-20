import { AuthChecker } from 'type-graphql';
import User from '../models/User';
import { ContextStructure } from './ContextStructure';

export interface AuthCheckerArgs {
    root: any;
    args: any;
    context: ContextStructure;
    info: any;
}

export const graphQLAuthenticationChecker: AuthChecker<any> = (
    { root, args, context, info }: AuthCheckerArgs,
    roles,
) => {
    const userEntity: User = context.userEntity;

    if (userEntity) {
        return roles.includes(userEntity.role);
    }
    return false;
};
