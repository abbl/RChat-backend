import bcrypt from 'bcrypt';
import { Service } from 'typedi';
import { Connection, getConnection, Repository } from 'typeorm';
import { ROLES } from '../constants/Roles';
import { SignUpInput } from '../graphql/resolvers/authentication/AuthenticationInputs';
import User from '../models/User';

@Service()
export default class UserService {
    private dbConnection: Connection = getConnection();
    private userRepository: Repository<User> = this.dbConnection.getRepository(User);

    public async createUser(signUpInput: SignUpInput): Promise<User | null> {
        if (
            (await this.isUsernameAvailable(signUpInput.username)) &&
            (await this.isEmailAvailable(signUpInput.email))
        ) {
            return await bcrypt.hash(signUpInput.password, 10).then(async encryptedPassword => {
                const user: User = {
                    username: signUpInput.username,
                    password: encryptedPassword,
                    email: signUpInput.email,
                    role: ROLES.user,
                };

                return await this.userRepository.save(user);
            });
        }
        return null;
    }

    public async isUsernameAvailable(username: string): Promise<boolean> {
        return this.userRepository.findOne({ where: { username: { $eq: username } } }).then(result => {
            return !Boolean(result);
        });
    }

    public async isEmailAvailable(email: string) {
        return this.userRepository.findOne({ where: { email: { $eq: email } } }).then(result => !Boolean(result));
    }
}
