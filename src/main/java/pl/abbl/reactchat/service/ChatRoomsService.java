package pl.abbl.reactchat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsService {
	List<ChatRoom> getPublicChatRooms();
}
