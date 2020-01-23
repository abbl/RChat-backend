import { Service } from 'typedi';
import { getConnection, Repository } from 'typeorm';
import RefreshToken from '../models/RefreshToken';
import User from '../models/User';
import uuid = require('uuid');

/**
 * A service handling distribution of refresh token, by default user is allowed to have 5 refresh tokens
 * at the same time.When user exceeds the limit, service removes the oldest token.
 * This service also saves the tokens into database and allows to fetch them.
 */
@Service()
export default class RefreshTokenService {
    /**
     * The limit of available tokens for one user.
     */
    private readonly REFRESH_TOKEN_LIMIT: number = 5;
    private refreshTokenRepository: Repository<RefreshToken> = getConnection().getRepository(RefreshToken);

    public async fetchRefreshTokens(user: User): Promise<RefreshToken[]> {
        const refreshTokens = this.refreshTokenRepository.find({ where: { belongsTo: user } });

        return refreshTokens;
    }

    /**
     * Creates the refresh token and saves it to database.
     * @param user
     */
    public async createRefreshToken(user: User): Promise<RefreshToken> {
        const refreshTokenEntity: RefreshToken = {
            token: this.generateRefreshToken(),
            belongsTo: user,
            expiresAt: this.calculateTokenExpirationDay(7),
        };
        const savedRefreshToken = this.saveRefreshToken(refreshTokenEntity, user);

        return savedRefreshToken;
    }

    /**
     * Saves the refresh token and removes the oldest one if user
     * exceeded the number of tokens he's able to have at the same time.
     */
    private async saveRefreshToken(refreshToken: RefreshToken, user: User): Promise<RefreshToken> {
        //Checking if user exceeds the limit of refresh tokens.
        if ((await this.getUserRefreshTokensNumber(user)) > this.REFRESH_TOKEN_LIMIT) {
            this.removeUserOldestRefreshToken(user);
        }
        const result = this.refreshTokenRepository.save(refreshToken);

        return result;
    }

    /**
     * Returns the number of tokens that user generated.
     * @param user
     */
    private async getUserRefreshTokensNumber(user: User): Promise<number> {
        const userRefreshTokens = await this.refreshTokenRepository.findAndCount({ where: { belongsTo: user } });

        return userRefreshTokens[1];
    }

    private async removeUserOldestRefreshToken(user: User): Promise<boolean> {
        const result = await this.refreshTokenRepository.findOne({
            where: { belongsTo: user },
            order: { expiresAt: 'ASC' },
        });

        return Boolean(this.refreshTokenRepository.remove(result));
    }

    private calculateTokenExpirationDay(expiresIn: number): Date {
        let expirationDate = new Date(Date.now());

        expirationDate.setDate(expirationDate.getDate() + expiresIn);

        return expirationDate;
    }

    private generateRefreshToken(): string {
        return uuid.v4();
    }
}
