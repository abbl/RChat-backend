import { AuthChecker } from 'type-graphql';

export const graphQLAuthenticationChecker: AuthChecker<any> = ({ root, args, context, info }, roles) => {
    // console.log(root);
    // console.log(args);
    console.log(context);
    // console.log(info);

    console.log(roles);

    return false;
};
