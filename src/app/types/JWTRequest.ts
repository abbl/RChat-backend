import { UserTokenContent } from '../authentication/UserTokenContent';

export interface JWTRequest extends Express.Request {
    user: UserTokenContent;
}
