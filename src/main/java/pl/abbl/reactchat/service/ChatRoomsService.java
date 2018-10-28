package pl.abbl.reactchat.service;

import java.util.List;
import java.util.Map;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;

import javax.servlet.http.HttpServletRequest;

public interface ChatRoomsService {
	List<ChatRoom> getPublicChatRooms();
	AbstractCallback saveChatRoom(Map<String, String> requestBody, HttpServletRequest request);
	AbstractCallback inviteUser(Map<String, String> requestBody, HttpServletRequest request);
	ChatRoom isChatRoomPrivate(int roomId);
	boolean isUserOwnerOfChatRoom(int roomId, int userId);
    List<ChatRoom> getUserChatRooms(HttpServletRequest request);
	AbstractCallback joinChatRoom(Map<String, String> requestBody, HttpServletRequest request);
}
