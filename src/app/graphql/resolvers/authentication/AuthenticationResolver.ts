import { Arg, Query, Resolver } from 'type-graphql';
import { Inject } from 'typedi';
import AuthenticationService from '../../../services/AuthenticationService';
import UserService from '../../../services/UserService';
import { RenewTokenInput, SignInInput, SignUpInput } from './AuthenticationInputs';
import { AuthenticationResultUnion } from './AuthenticationResolverUnions';

@Resolver()
export default class AuthenticationResolver {
    @Inject()
    private authenticationService: AuthenticationService;

    @Inject()
    private userService: UserService;

    @Query(returns => AuthenticationResultUnion)
    public async signIn(@Arg('data') signInInput: SignInInput): Promise<typeof AuthenticationResultUnion> {
        const result = await this.authenticationService.signIn(signInInput.username, signInInput.password);

        return result;
    }

    @Query(returns => Boolean)
    public async signUp(@Arg('data') signUpInput: SignUpInput) {
        const result = await this.userService.createUser(signUpInput.username, signUpInput.password, signUpInput.email);

        return Boolean(result);
    }

    @Query(returns => String)
    public async renewToken(@Arg('data') renewTokenInput: RenewTokenInput) {
        return '';
    }
}
