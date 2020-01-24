import { UserTokenContent } from '../authentication/UserTokenContent';

export interface RequestWithJWT extends Express.Request {
    user: UserTokenContent;
}
