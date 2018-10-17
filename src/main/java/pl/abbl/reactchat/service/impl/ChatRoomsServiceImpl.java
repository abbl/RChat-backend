package pl.abbl.reactchat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatCreationCallback;
import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.entity.ChatRoomParticipants;
import pl.abbl.reactchat.entity.ChatUser;
import pl.abbl.reactchat.repository.ChatRoomParticipantsRepository;
import pl.abbl.reactchat.repository.ChatRoomRepository;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.repository.enums.ChatRoomType;
import pl.abbl.reactchat.service.ChatRoomsService;

import javax.servlet.http.HttpServletRequest;

import static pl.abbl.reactchat.definitions.PostParametersConstants.*;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ChatRoomParticipantsRepository chatRoomParticipantsRepository;

	@Override
	public List<ChatRoom> getPublicChatRooms() {
		List<ChatRoom> publicChatRooms = new ArrayList<>();

		for(ChatRoom chatRoom : chatRoomRepository.findAll()){
			if(chatRoom.getType() == ChatRoomType.PUBLIC){
				publicChatRooms.add(chatRoom);
			}
		}

		return publicChatRooms;
	}

	@Override
	public List<ChatRoom> getPrivateChatRooms(HttpServletRequest request) {
		ChatUser chatUser = userRepository.findByJwtToken(request);

		if(chatUser != null){
			List<Integer> chatRooms = new ArrayList<>();

			for (ChatRoomParticipants chatRoomParticipant : chatRoomParticipantsRepository.findChatRoomsByUserId(chatUser.getId())) {
				chatRooms.add(chatRoomParticipant.getRoomId());
			}
			return chatRoomRepository.findChatRoomsByListOfId(chatRooms);
		}
		return null;
	}

	@Override
	public AbstractCallback saveChatRoom() {
		/*
				ChatUser chatUser = userRepository.findByJwtToken(request);
		boolean isChatRoomTypeCorrect = false;

		if(chatUser != null){
			String chatRoomName = chatMessage.get(CHAT_ROOM_NAME);
			String chatRoomDesc = chatMessage.get(CHAT_ROOM_DESC);
			String chatRoomType = chatMessage.get(CHAT_ROOM_TYPE);

			if(chatRoomName != null && chatRoomDesc != null && chatRoomType != null){
				for (String roomType : AVAILABLE_CHAT_ROOM_TYPES) {
					if(roomType == chatRoomType)
						isChatRoomTypeCorrect = true;
				}

				if(isChatRoomTypeCorrect){

				}else{
					return new ChatCreationCallback(ChatCreationCallback.INVALID_CHAT_ROOM_TYPE);
				}
			}
		}
		return null;
		 */
		return null;
	}

	public ChatRoom isChatRoomPrivate(int roomId){
		return chatRoomRepository.isChatRoomPrivate(roomId);
	}
}
