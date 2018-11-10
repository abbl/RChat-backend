package pl.abbl.reactchat.services;

import pl.abbl.reactchat.entities.RoomRight;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;

import java.util.List;

public interface RoomRightService {
    /**
     * @return true only if right is equal or greater.
     */
    boolean isUserRightLevelHighEnough(int userId, int roomId, RoomRightLevel rightLevel);
    RoomRight getUserRight(int userId, int roomId);
    List<RoomRight> getAllUserRights(int userId);
    boolean doesUserHaveAnyRight(int userId, int roomId);
    void addUserRight(int userId, int roomId, RoomRightLevel rightLevel);
    void updateUserRight(int userId, int roomId, RoomRightLevel rightLevel);
}
