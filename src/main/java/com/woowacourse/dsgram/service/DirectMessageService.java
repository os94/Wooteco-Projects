package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.ChatMessage;
import com.woowacourse.dsgram.domain.ChatRoom;
import com.woowacourse.dsgram.domain.ChatUser;
import com.woowacourse.dsgram.domain.User;
import com.woowacourse.dsgram.domain.repository.ChatMessageRepository;
import com.woowacourse.dsgram.domain.repository.ChatRoomRepository;
import com.woowacourse.dsgram.domain.repository.ChatUserRepository;
import com.woowacourse.dsgram.service.assembler.ChatAssembler;
import com.woowacourse.dsgram.service.dto.chat.ChatMessageRequest;
import com.woowacourse.dsgram.service.dto.chat.ChatMessageResponse;
import com.woowacourse.dsgram.service.dto.chat.ChatMessagesRequest;
import com.woowacourse.dsgram.service.exception.NotFoundChatRoomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class DirectMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatUserRepository chatUserRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final UserService userService;

    public DirectMessageService(ChatMessageRepository chatMessageRepository, ChatUserRepository chatUserRepository, ChatRoomRepository chatRoomRepository, UserService userService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatUserRepository = chatUserRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userService = userService;
    }

    private long makePrivateRoom(long id1, long id2) {
        return id1 > id2 ? Objects.hash(id1, id2) : Objects.hash(id2, id1);
    }

    public long enterChatRoom(long id1, long id2) {
        long roomCode = makePrivateRoom(id1, id2);
        Optional<ChatRoom> maybeChatRoom = findByRoomCode(roomCode);
        ChatRoom chatRoom = maybeChatRoom.orElseGet(() -> initializeChatRoom(id1, id2));
        return chatRoom.getCode();
    }

    private ChatRoom initializeChatRoom(long id1, long id2) {
        long roomCode = makePrivateRoom(id1, id2);
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(roomCode));

        User user1 = userService.findUserById(id1);
        User user2 = userService.findUserById(id2);
        chatUserRepository.saveAll(Arrays.asList(new ChatUser(chatRoom, user1), new ChatUser(chatRoom, user2)));
        return chatRoom;
    }

    public ChatMessageResponse saveMessage(long roomCode, ChatMessageRequest chatMessageRequest) {
        ChatRoom chatRoom = findByRoomCode(roomCode)
                .orElseThrow(() -> new NotFoundChatRoomException("채팅방을 찾을 수 없습니다."));
        User user = userService.findUserById(chatMessageRequest.getFrom());
        ChatMessage chatMessage = chatMessageRepository
                .save(new ChatMessage(chatMessageRequest.getContent(), user, chatRoom));
        return ChatAssembler.toChatMessageResponse(chatMessage);
    }

    private Optional<ChatRoom> findByRoomCode(long roomCode) {
        return chatRoomRepository.findByCode(roomCode);
    }

    @Transactional(readOnly = true)
    public ChatMessagesRequest getPreviousChatMessages(long roomCode) {
        List<ChatMessage> prevMessages = chatMessageRepository.findAllByChatRoomCode(roomCode);
        return ChatAssembler.toChatMessagesRequest(prevMessages);
    }
}
