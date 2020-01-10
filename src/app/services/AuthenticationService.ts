import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';
import { Inject, Service } from 'typedi';
import { getConnection, Repository } from 'typeorm';
import uuid from 'uuid';
import AuthenticationResult from '../authentication/AuthenticationResult';
import { UserTokenContent } from '../authentication/UserTokenContent';
import RefreshToken from '../models/RefreshToken';
import User from '../models/User';
import UserService from './UserService';

@Service()
export default class AuthenticationService {
    @Inject()
    private userService: UserService;

    private tokenSecret: string = process.env.API_SECRET;

    private refreshTokenRepository: Repository<RefreshToken> = getConnection().getRepository(RefreshToken);

    /**
     * After successful authentication returns JWT and RefreshToken.
     * @param signInInput
     */
    public async signIn(username: string, password: string): Promise<AuthenticationResult | null> {
        const user = await this.userService.findOneByUsername(username);

        if (user) {
            const isPasswordCorrect = await bcrypt.compare(password, user.password);

            if (isPasswordCorrect) {
                const authorizationKeys = this.createAuthorizationKeys(username, user.role);
                await this.saveRefreshToken(authorizationKeys.refreshToken, user);

                return authorizationKeys;
            }
        }

        return null;
    }

    private async saveRefreshToken(refreshToken: string, user: User): Promise<void> {
        const refreshTokenEntity: RefreshToken = {
            token: refreshToken,
            belongsTo: user,
            expiresAt: this.calculateTokenExpirationDay(7),
        };

        await this.refreshTokenRepository.save(refreshTokenEntity);
    }

    private calculateTokenExpirationDay(expiresIn: number) {
        let expirationDate = new Date(Date.now());

        expirationDate.setDate(expirationDate.getDate() + expiresIn);

        return expirationDate;
    }

    private createAuthorizationKeys(username: string, role: string): AuthenticationResult {
        return { authenticationToken: this.createJWT(username, role), refreshToken: this.createRefreshToken() };
    }

    private createJWT(username: string, role: string): string {
        const userTokenContent: UserTokenContent = { username: username, role: role };

        return jwt.sign(userTokenContent, this.tokenSecret, { expiresIn: '7d' });
    }

    private createRefreshToken(): string {
        return uuid.v4();
    }
}
