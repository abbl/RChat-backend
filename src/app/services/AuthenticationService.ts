import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Inject, Service } from 'typedi';
import AuthenticationTokens from '../authentication/AuthenticationResult';
import { UserTokenContent } from '../authentication/UserTokenContent';
import { AUTHENTICATION_INCORRECT_CREDENTIALS } from '../constants/ErrorCodes';
import ErrorResponse from '../graphql/resolvers/shared/ErrorResponse';
import User from '../models/User';
import RefreshTokenService from './RefreshTokenService';
import UserService from './UserService';

/**
 * AuthenticationService takes care of SignIn process by exposing signIn method
 * which returns AuthenticationTokens or ErrorResponse based on credentials provided
 * by user.
 */
@Service()
export default class AuthenticationService {
    @Inject()
    private userService: UserService;

    @Inject()
    private refreshTokenService: RefreshTokenService;

    private readonly tokenSecret: string = process.env.API_SECRET;

    /**
     * Checks username and password provided by user and returns AuthenticationTokens object
     * upon successful authentication or ErrorResponse If credentials are incorrect.
     * @param signInInput
     */
    public async signIn(username: string, password: string): Promise<AuthenticationTokens | ErrorResponse> {
        const user = await this.userService.findOneByUsername(username);

        if (user) {
            const isPasswordCorrect = await bcrypt.compare(password, user.password);

            if (isPasswordCorrect) {
                const authorizationKeys = await this.createAuthenticationTokens(user);

                return Object.assign(new AuthenticationTokens(), authorizationKeys);
            }
        }

        return Object.assign(new ErrorResponse(), {
            code: AUTHENTICATION_INCORRECT_CREDENTIALS,
            message: 'Username or password is incorrect',
        });
    }

    /**
     * Creates tokens required to access restricted parts of the API.
     * @param user
     */
    private async createAuthenticationTokens(user: User): Promise<AuthenticationTokens> {
        return {
            authenticationToken: this.createJWT(user.username, user.role),
            refreshToken: (await this.refreshTokenService.createRefreshToken(user)).token,
        };
    }

    private createJWT(username: string, role: string): string {
        const userTokenContent: UserTokenContent = { username: username, role: role };

        return jwt.sign(userTokenContent, this.tokenSecret, { expiresIn: '7d' });
    }
}
