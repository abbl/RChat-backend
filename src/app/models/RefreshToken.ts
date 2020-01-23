import { Column, Entity, JoinColumn, ManyToOne, ObjectID, PrimaryGeneratedColumn } from 'typeorm';
import User from './User';

@Entity()
export default class RefreshToken {
    @PrimaryGeneratedColumn()
    _id?: ObjectID;

    @Column()
    token: string;

    @Column()
    expiresAt: Date;

    @ManyToOne(type => User)
    @JoinColumn()
    belongsTo: User;
}
