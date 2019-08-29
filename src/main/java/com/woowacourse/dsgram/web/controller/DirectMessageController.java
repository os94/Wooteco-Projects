package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.service.DirectMessageService;
import com.woowacourse.dsgram.service.dto.chat.ChatMessageRequest;
import com.woowacourse.dsgram.service.dto.chat.ChatMessageResponse;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DirectMessageController {
    private final DirectMessageService directMessageService;

    public DirectMessageController(DirectMessageService directMessageService) {
        this.directMessageService = directMessageService;
    }

    @GetMapping("/chat/{receiver}")
    public String showDMPage(@PathVariable long receiver, @UserSession LoggedInUser loggedInUser, Model model) {
        model.addAttribute(
                "roomCode",
                directMessageService.enterChatRoom(receiver, loggedInUser.getId()));
        return "room";
    }

    @ResponseBody
    @GetMapping("/api/chat/messages/{roomCode}")
    public ResponseEntity getPreviousMessages(@PathVariable int roomCode) {
        return ResponseEntity.ok(directMessageService.getPreviousChatMessages(roomCode));
    }

    @MessageMapping("/chat/{roomCode}")
    @SendTo("/topic/open/{roomCode}")
    public ChatMessageResponse handleMessage(@DestinationVariable long roomCode, ChatMessageRequest chatMessageRequest) {
        return directMessageService.saveMessage(roomCode, chatMessageRequest);
    }
}
