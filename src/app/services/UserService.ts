import bcrypt from 'bcrypt';
import { Service } from 'typedi';
import { getConnection, Repository } from 'typeorm';
import { ROLES } from '../constants/Roles';
import User from '../models/User';

@Service()
export default class UserService {
    private userRepository: Repository<User> = getConnection().getRepository(User);

    public async findOneByUsername(username: string): Promise<User> {
        return this.userRepository.findOne({ where: { username: username } });
    }

    public async findOneById(id: string): Promise<User> {
        return this.userRepository.findOne({ where: { id: id } });
    }

    public async createUser(username: string, password: string, email: string): Promise<User | null> {
        if ((await this.isUsernameAvailable(username)) && (await this.isEmailAvailable(email))) {
            return await bcrypt.hash(password, 10).then(async encryptedPassword => {
                const user: User = {
                    username: username,
                    password: encryptedPassword,
                    email: email,
                    role: ROLES.user,
                };

                return await this.userRepository.save(user);
            });
        }
        return null;
    }

    private async isUsernameAvailable(username: string): Promise<boolean> {
        return await this.findOneByUsername(username).then(result => {
            return !Boolean(result);
        });
    }

    private async isEmailAvailable(email: string): Promise<boolean> {
        return this.userRepository.findOne({ where: { email: email } }).then(result => !Boolean(result));
    }
}
