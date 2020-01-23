import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Inject, Service } from 'typedi';
import AuthenticationResult from '../authentication/AuthenticationResult';
import { UserTokenContent } from '../authentication/UserTokenContent';
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
    public async signIn(username: string, password: string): Promise<AuthenticationResult | null> {
        const user = await this.userService.findOneByUsername(username);

        if (user) {
            const isPasswordCorrect = await bcrypt.compare(password, user.password);

            if (isPasswordCorrect) {
                const authorizationKeys = this.createAuthorizationKeys(user);

                return authorizationKeys;
            }
        }

        return null;
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
