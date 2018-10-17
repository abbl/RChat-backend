package pl.abbl.reactchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public AbstractCallback saveChatRoom(Map<String, String> requestBody, HttpServletRequest request) {
		ChatUser chatUser = userRepository.findByJwtToken(request);
		boolean isChatRoomTypeCorrect = false;

		if(chatUser != null){
			String chatRoomName = requestBody.get(CHAT_ROOM_NAME);
			String chatRoomDesc = requestBody.get(CHAT_ROOM_DESC);
			String chatRoomType = requestBody.get(CHAT_ROOM_TYPE);

			if(chatRoomName != null && chatRoomDesc != null && chatRoomType != null){
				for (String roomType : AVAILABLE_CHAT_ROOM_TYPES) {
					if(roomType == chatRoomType)
						isChatRoomTypeCorrect = true;
				}

				if(isChatRoomTypeCorrect){
					chatRoomRepository.saveAndFlush(createChatRoom(chatRoomName, chatRoomDesc, chatRoomType, chatUser.getId()));
					ChatRoom newChatRoom = chatRoomRepository.findChatRoomByName(chatRoomName);
					chatRoomParticipantsRepository.saveAndFlush(createChatRoomParticipant(newChatRoom.getId(), chatUser.getId()));
				}else{
					return new ChatCreationCallback(ChatCreationCallback.INVALID_CHAT_ROOM_TYPE);
				}
			}
		}
		return null;
	}

	private ChatRoom createChatRoom(String chatRoomName, String chatRoomDesc, String chatRoomType, int ownerId){
		ChatRoom chatRoom = new ChatRoom();
		chatRoom.setName(chatRoomName);
		chatRoom.setDescription(chatRoomDesc);
		chatRoom.setType(ChatRoomType.valueOf(chatRoomType));
		chatRoom.setOwnerId(ownerId);

		return chatRoom;
	}

	private ChatRoomParticipants createChatRoomParticipant(int roomId, int userId){
		ChatRoomParticipants chatRoomParticipants = new ChatRoomParticipants();
		chatRoomParticipants.setUserId(userId);
		chatRoomParticipants.setRoomId(roomId);
		return chatRoomParticipants;
	}

	public ChatRoom isChatRoomPrivate(int roomId){
		return chatRoomRepository.isChatRoomPrivate(roomId);
	}
}
