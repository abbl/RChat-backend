import { Arg, Query, Resolver } from 'type-graphql';
import { Inject } from 'typedi';
import AuthenticationService from '../../../services/AuthenticationService';
import UserService from '../../../services/UserService';
import { RefreshAuthenticationTokenInput, SignInInput, SignUpInput } from './AuthenticationInputs';
import AuthenticationTokens from './AuthenticationTokens';

@Resolver()
export default class AuthenticationResolver {
    @Inject()
    private authenticationService: AuthenticationService;

    @Inject()
    private userService: UserService;

    @Query(returns => AuthenticationTokens)
    public async signIn(@Arg('data') signInInput: SignInInput): Promise<AuthenticationTokens> {
        const result = await this.authenticationService.signIn(signInInput.username, signInInput.password);

        return result;
    }

    @Query(returns => Boolean)
    public async signUp(@Arg('data') signUpInput: SignUpInput): Promise<boolean> {
        const result = await this.userService.createUser(signUpInput.username, signUpInput.password, signUpInput.email);

        return Boolean(result);
    }

    @Query(returns => AuthenticationTokens)
    public async renewToken(@Arg('data') refreshAuthenticationToken: RefreshAuthenticationTokenInput) {
        const result = await this.authenticationService.refreshAuthenticationToken(refreshAuthenticationToken);

        return result;
    }
}
