import { Column, Entity, JoinColumn, JoinTable, ManyToMany, ManyToOne, PrimaryGeneratedColumn } from 'typeorm';
import User from './User';

/**
 * Model representing ChatRoom entity.
 */
@Entity()
export class Chatroom {
    @PrimaryGeneratedColumn()
    id?: string;

    @Column()
    name: string;

    @Column()
    description: string;

    @ManyToOne(type => User, { eager: true })
    @JoinColumn()
    owner: User;

    @Column()
    isPrivate: boolean;

    @ManyToMany(type => User, { cascade: true })
    @JoinTable()
    users?: User[];
}
