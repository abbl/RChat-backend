package pl.abbl.reactchat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatRoomCallback;
import pl.abbl.reactchat.definitions.PostParametersConstants;
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
					return new ChatRoomCallback(ChatRoomCallback.INVALID_CHAT_ROOM_TYPE);
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

	public AbstractCallback inviteUser(Map<String, String> requestBody, HttpServletRequest request){
		ChatUser ownerOfChatRoom = userRepository.findByJwtToken(request);
		int roomId = Integer.parseInt(requestBody.get(PostParametersConstants.CHAT_ROOM_INVITE_ROOM_ID));

		if(isChatRoomPrivate(roomId) == null){
			if(isUserOwnerOfChatRoom(roomId, ownerOfChatRoom.getId())){
				ChatUser invitedUser = userRepository.findByUsername(requestBody.get(PostParametersConstants.CHAT_ROOM_INVITE_USERNAME));

				if(invitedUser != null){
					ChatRoomParticipants chatRoomParticipants = new ChatRoomParticipants();
					chatRoomParticipants.setRoomId(roomId);
					chatRoomParticipants.setUserId(invitedUser.getId());
					chatRoomParticipantsRepository.saveAndFlush(chatRoomParticipants);
				}else{
					return new ChatRoomCallback(ChatRoomCallback.NO_SUCH_USER_FOUND);
				}
			}else{
				return new ChatRoomCallback(ChatRoomCallback.MISSING_OWNER_RIGHTS);
			}
		}else{
			return new ChatRoomCallback(ChatRoomCallback.CANT_INVITE_TO_PUBLIC_ROOM);
		}

		return null;
	}

	public ChatRoom isChatRoomPrivate(int roomId){
		return chatRoomRepository.isChatRoomPrivate(roomId);
	}

	@Override
	public boolean isUserOwnerOfChatRoom(int roomId, int userId) {
		return chatRoomRepository.isUserOwnerOfChatRoom(roomId, userId) != null;
	}
}
