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
import pl.abbl.reactchat.entity.ChatRoomParticipant;
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
	public AbstractCallback joinChatRoom(Map<String, String> requestBody, HttpServletRequest request) {
		ChatUser chatUser = userRepository.findByJwtToken(request);

		if(chatUser != null){
			int roomId = Integer.parseInt(requestBody.get(PostParametersConstants.CHAT_ROOM_JOIN_ROOM_ID));
			ChatRoom isRoomPrivate = chatRoomRepository.isChatRoomPrivate(roomId);

			if(isRoomPrivate == null){
				if(chatRoomParticipantsRepository.isUserParticipantOfChatRoom(chatUser.getId(), roomId) == null){
					chatRoomParticipantsRepository.saveAndFlush(new ChatRoomParticipant(chatUser.getId(), roomId));
				}else{
					return new ChatRoomCallback(ChatRoomCallback.YOU_ARE_ALREADY_MEMBER_OF_THIS_CHANNEL);
				}
			}else{
				return new ChatRoomCallback(ChatRoomCallback.INVALID_CHAT_ROOM_TYPE);
			}
		}
		return null;
	}

	@Override
	public List<ChatRoom> getUserChatRooms(HttpServletRequest request) {
		ChatUser chatUser = userRepository.findByJwtToken(request);

		if(chatUser != null){
			List<Integer> chatRooms = new ArrayList<>();

			for (ChatRoomParticipant chatRoomParticipant : chatRoomParticipantsRepository.findChatRoomsByUserId(chatUser.getId())) {
				chatRooms.add(chatRoomParticipant.getRoomId());
			}

			if(!chatRooms.isEmpty())
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
				String fixedChatRoomName = removeUnnecessaryCharsFromChatRoomName(chatRoomName);

				if(!chatRoomRepository.findChatRoomByName(fixedChatRoomName).isEmpty())
					return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_NAME_TAKEN);

				for (String roomType : AVAILABLE_CHAT_ROOM_TYPES) {
					if(roomType.equals(chatRoomType))
						isChatRoomTypeCorrect = true;
				}

				if(isChatRoomTypeCorrect){
					chatRoomRepository.saveAndFlush(new ChatRoom(fixedChatRoomName, chatRoomDesc, chatRoomType, chatUser.getId()));
					ChatRoom newChatRoom = chatRoomRepository.findChatRoomByName(fixedChatRoomName).get(0);
					chatRoomParticipantsRepository.saveAndFlush(new ChatRoomParticipant(newChatRoom.getId(), chatUser.getId()));
					return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_CREATED_SUCCESSFULLY);
				}else{
					return new ChatRoomCallback(ChatRoomCallback.INVALID_CHAT_ROOM_TYPE);
				}
			}
		}
		return null;
	}

	private String removeUnnecessaryCharsFromChatRoomName(String chatRoomName){
		StringBuilder stringBuilder = new StringBuilder(chatRoomName);

		while(stringBuilder.charAt(0) == '#'){
			stringBuilder.deleteCharAt(0);
		}
		stringBuilder.insert(0, '#');

		return stringBuilder.toString();
	}

	public AbstractCallback inviteUser(Map<String, String> requestBody, HttpServletRequest request){
		ChatUser ownerOfChatRoom = userRepository.findByJwtToken(request);
		int roomId = Integer.parseInt(requestBody.get(PostParametersConstants.CHAT_ROOM_INVITE_ROOM_ID));

		if(isChatRoomPrivate(roomId) == null){
			if(isUserOwnerOfChatRoom(roomId, ownerOfChatRoom.getId())){
				ChatUser invitedUser = userRepository.findByUsername(requestBody.get(PostParametersConstants.CHAT_ROOM_INVITE_USERNAME));

				if(invitedUser != null){
					chatRoomParticipantsRepository.saveAndFlush(new ChatRoomParticipant(roomId, invitedUser.getId()));
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
