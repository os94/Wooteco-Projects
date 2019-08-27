package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.Message;
import com.woowacourse.dsgram.service.DirectMessageService;
import com.woowacourse.dsgram.service.dto.user.LoggedInUser;
import com.woowacourse.dsgram.web.argumentresolver.UserSession;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DirectMessageController {
    private final DirectMessageService directMessageService;

    public DirectMessageController(DirectMessageService directMessageService) {
        this.directMessageService = directMessageService;
    }

    @GetMapping("/dm")
    public String showDMPage(long to, @UserSession LoggedInUser loggedInUser, Model model) {
        int roomId = directMessageService.makePrivateRoom(to, loggedInUser.getId());
        model.addAttribute("roomId", roomId);
        return "room";
    }

    @MessageMapping("/dm/{roomId}")
    @SendTo("/topic/open/{roomId}")
    public Message greeting(@DestinationVariable long roomId, Message message) throws Exception {
        System.out.println("ollaph" + message);
        return message;
    }
}
