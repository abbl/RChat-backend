import { AuthChecker } from 'type-graphql';
import { RequestContextStructure } from './RequestContextStructure';
import { UserTokenContent } from './UserTokenContent';

export interface AuthCheckerArgs {
    root: any;
    args: any;
    context: RequestContextStructure;
    info: any;
}

export const graphQLAuthenticationChecker: AuthChecker<any> = (
    { root, args, context, info }: AuthCheckerArgs,
    roles,
) => {
    const userTokenContent: UserTokenContent = context.user;

    if (userTokenContent) {
        return roles.includes(userTokenContent.role);
    }
    return false;
};
