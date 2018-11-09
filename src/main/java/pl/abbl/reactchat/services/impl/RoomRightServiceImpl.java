package pl.abbl.reactchat.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.entities.RoomRight;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;
import pl.abbl.reactchat.repositories.RoomRightRepository;
import pl.abbl.reactchat.services.RoomRightService;

@Service
public class RoomRightServiceImpl implements RoomRightService {
    @Autowired
    private RoomRightRepository roomRightRepository;

    private static Logger logger = LoggerFactory.getLogger(RoomRightServiceImpl.class);

    @Override
    public RoomRight getUserRight(int userId, int roomId) {
        return roomRightRepository.getRight(userId, roomId);
    }

    @Override
    public boolean doesUserHaveAnyRight(int userId, int roomId) {
        return getUserRight(userId, roomId) != null;
    }

    @Override
    public boolean isUserRightLevelHighEnough(int userId, int roomId, RoomRightLevel rightLevel) {
        RoomRight roomRight = roomRightRepository.getRight(userId, roomId);

        if(roomRight != null){
            return roomRight.getRightLevel().ordinal() <= rightLevel.ordinal();
        }
        return false;
    }

    //TODO add callbacks to those methods, check if it is a good idea to expose what happens with data to API users.

    @Override
    public void addUserRight(int userId, int roomId, RoomRightLevel rightLevel) {
        if(userId == 0 || roomId == 0)
            logger.warn("AddUserRight() received a 0 in userId or roomId variable.[There is now way that any user or room has primary key of 0](Record might be added to database)");

        if(!doesUserHaveAnyRight(userId, roomId) && rightLevel != null){ //TODO check if such room exists. (NO CHATROOM SERVICE IMPL AT THIS MOMENT TO HANDLE THIS)
            roomRightRepository.saveAndFlush(new RoomRight(userId, roomId, rightLevel));
        }
    }

    @Override
    public void updateUserRight(int userId, int roomId, RoomRightLevel rightLevel) {
        RoomRight roomRight = getUserRight(userId, roomId);

        if(rightLevel != null && roomRight != null){
            roomRight.setRightLevel(rightLevel);
            roomRightRepository.saveAndFlush(roomRight);
        }
    }
}
