import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Inject, Service } from 'typedi';
import { UserTokenContent } from '../authentication/UserTokenContent';
import { AUTHENTICATION_INCORRECT_CREDENTIALS, AUTHENTICATION_INCORRECT_REFRESH_TOKEN } from '../constants/ErrorCodes';
import { RefreshAuthenticationTokenInput } from '../graphql/resolvers/authentication/AuthenticationInputs';
import AuthenticationTokens from '../graphql/resolvers/authentication/AuthenticationResult';
import ErrorResponse from '../graphql/resolvers/shared/ErrorResponse';
import RefreshToken from '../models/RefreshToken';
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
        const user: User | undefined = await this.userService.findOneByUsername(username);

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
     * Creates new authentication token based on provided refresh token.
     * @param refreshAuthenticationToken
     */
    public async refreshAuthenticationToken(refreshAuthenticationToken: RefreshAuthenticationTokenInput) {
        const refreshTokenEntity: RefreshToken | undefined = await this.refreshTokenService.findRefreshToken(
            refreshAuthenticationToken.refreshToken,
        );

        //Checking if provided token was found in database.
        if (refreshTokenEntity) {
            const userEntity: User = refreshTokenEntity.belongsTo;

            return Object.assign(new AuthenticationTokens(), {
                authenticationToken: this.createJWT(userEntity),
                refreshToken: refreshAuthenticationToken.refreshToken,
            });
        }

        return Object.assign(new ErrorResponse(), {
            code: AUTHENTICATION_INCORRECT_REFRESH_TOKEN,
            message: 'Provided refresh token is incorrect or expired',
        });
    }

    /**
     * Creates tokens required to access restricted parts of the API.
     * @param user
     */
    private async createAuthenticationTokens(user: User): Promise<AuthenticationTokens> {
        return {
            authenticationToken: this.createJWT(user),
            refreshToken: (await this.refreshTokenService.createRefreshToken(user)).token,
        };
    }

    private createJWT(user: User): string {
        const userTokenContent: UserTokenContent = { username: user.username, role: user.role };

        return jwt.sign(userTokenContent, this.tokenSecret, { expiresIn: '7d' });
    }
}
