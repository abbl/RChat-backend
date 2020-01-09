import { Service } from 'typedi';
import { SignInInput } from '../graphql/resolvers/authentication/AuthenticationInputs';

@Service()
export default class AuthenticationService {
    public async signIn(signInInput: SignInInput) {}
}
