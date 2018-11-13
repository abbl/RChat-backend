package pl.abbl.reactchat.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
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
                "`time` VARCHAR(255) NOT NULL, PRIMARY KEY (`id`)) " +
                "ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci")
                .executeUpdate();
    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {

    }

    @Override
    public List<ChatMessage> findLastMessagesByRange(ChatRoom chatRoom, int range) {
        if(chatRoom != null && range <= FETCH_RANGE_LIMIT){

        }
        return null;
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        return null;
    }

    private void testMethod(){
        List list = entityManager.createNativeQuery("SELECT * FROM `1`", ChatMessage.class).getResultList();
        System.out.println("Test:" + list.toString());
    }
}
