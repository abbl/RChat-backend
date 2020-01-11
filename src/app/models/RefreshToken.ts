import { Column, Entity, JoinColumn, ObjectID, OneToOne, PrimaryGeneratedColumn } from 'typeorm';
import User from './User';

@Entity()
export default class RefreshToken {
    @PrimaryGeneratedColumn()
    _id?: ObjectID;

    @Column()
    token: string;

    @Column()
    expiresAt: Date;

    @OneToOne(type => User)
    @JoinColumn()
    belongsTo: User;
}
