package pl.abbl.reactchat.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static pl.abbl.reactchat.configs.ReactChatConfiguration.FETCH_RANGE_LIMIT;

/**
 * @inheritDoc
 */
@Repository
public class ChatContentRepositoryImpl implements ChatContentRepository {
    @Autowired
    @Qualifier("chatRoomContentEntityManager")
    private EntityManager entityManager;

    @Transactional
    @Override
    public void createChatContentTable(ChatRoom chatRoom) {
        entityManager.joinTransaction();

        entityManager.createNativeQuery("CREATE TABLE `" + chatRoom.getId() + "` (" +
                "`id` INT NOT NULL AUTO_INCREMENT, " +
                "`author` VARCHAR(64) NOT NULL, `content` TEXT NOT NULL, " +
                "`time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (`id`)) " +
                "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci")
                .executeUpdate();
    }

    @Transactional
    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {
        entityManager.joinTransaction();

        entityManager.createNativeQuery("INSERT INTO `" + chatRoom.getId() + "` (author, content) VALUES (:author, :content)")
                .setParameter("author", chatMessage.getAuthor())
                .setParameter("content", chatMessage.getContent())
                .executeUpdate();
    }

    @Override
    public List<ChatMessage> findLastMessagesByRange(ChatRoom chatRoom, int range) {
        if(chatRoom != null && range <= FETCH_RANGE_LIMIT){
            return entityManager.createNativeQuery("SELECT * FROM `" + chatRoom.getId() + "` LIMIT " + range, ChatMessage.class).getResultList();
        }
        return new ArrayList<>();
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        if(chatRoom != null && Math.abs((start) - end) < FETCH_RANGE_LIMIT){
            return entityManager.createNativeQuery("SELECT * FROM `" + chatRoom.getId() + "` LIMIT " + end + " OFFSET " + (--start) , ChatMessage.class).getResultList();
        }
        return new ArrayList<>();
    }
}
