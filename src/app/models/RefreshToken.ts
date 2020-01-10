import { Column, Entity, JoinColumn, ObjectID, ObjectIdColumn, OneToOne } from 'typeorm';
import User from './User';

@Entity()
export default class RefreshToken {
    @ObjectIdColumn()
    _id?: ObjectID;

    @Column()
    token: string;

    @Column()
    expiresAt: Date;

    @OneToOne(type => User)
    @JoinColumn()
    belongsTo: User;
}
