import { Connection, createConnection } from 'typeorm';
import DatabaseType from '../constants/DatabaseType';

/**
 * DatabaseManager connects to databases used by the application
 * and checks if connections were successful.
 */
export default abstract class DatabaseManager {
    public static async initializeDatabases(): Promise<void> {
        await this.initializeMainDatabase();
        await this.initializeChatroomsContentDatabase();
    }

    /**
     * Creates connection to main database storing models defined in src/app/models/*.
     */
    private static async initializeMainDatabase(): Promise<void> {
        const connection = await createConnection({
            name: DatabaseType.MAIN_DATABASE,
            type: 'postgres',
            host: 'localhost',
            port: 5432,
            username: 'postgres',
            password: '123',
            database: 'rchat',
            synchronize: true,
            entities: ['src/app/models/*.*'],
        });

        this.isConnected(connection);
    }

    /**
     * Creates connection to database storing content of each chatroom.
     */
    private static async initializeChatroomsContentDatabase(): Promise<void> {
        const connection = await createConnection({
            name: DatabaseType.CHATROOMS_CONTENT_DATABASE,
            type: 'postgres',
            host: 'localhost',
            port: 5432,
            username: 'postgres',
            password: '123',
            database: 'rchat_chatrooms_content',
        });

        this.isConnected(connection);
    }

    /**
     * Checks If connection to database was established successfully.
     * Throws error if connection wasn't established.
     */
    private static isConnected(connection: Connection): void {
        if (connection.isConnected) {
            console.log(`Connected to database ${connection.name}.`);
        } else {
            throw new Error(`Can't connect to database ${connection.name}.`);
        }
    }
}
