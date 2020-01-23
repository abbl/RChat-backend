import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Inject, Service } from 'typedi';
import AuthenticationResult from '../authentication/AuthenticationResult';
import { UserTokenContent } from '../authentication/UserTokenContent';
import { AUTHENTICATION_INCORRECT_CREDENTIALS } from '../constants/ErrorCodes';
import ErrorResponse from '../graphql/resolvers/shared/ErrorResponse';
import User from '../models/User';
import RefreshTokenService from './RefreshTokenService';
import UserService from './UserService';

@Service()
export default class AuthenticationService {
    @Inject()
    private userService: UserService;

    @Inject()
    private refreshTokenService: RefreshTokenService;

    private tokenSecret: string = process.env.API_SECRET;

    /**
     * After successful authentication returns JWT and RefreshToken.
     * @param signInInput
     */
    public async signIn(username: string, password: string): Promise<AuthenticationResult | ErrorResponse> {
        const user = await this.userService.findOneByUsername(username);

        if (user) {
            const isPasswordCorrect = await bcrypt.compare(password, user.password);

            if (isPasswordCorrect) {
                const authorizationKeys = await this.createAuthorizationKeys(user);

                return Object.assign(new AuthenticationResult(), authorizationKeys);
            }
        }

        return Object.assign(new ErrorResponse(), {
            code: AUTHENTICATION_INCORRECT_CREDENTIALS,
            message: 'Username or password is incorrect',
        });
    }

    private async createAuthorizationKeys(user: User): Promise<AuthenticationResult> {
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
