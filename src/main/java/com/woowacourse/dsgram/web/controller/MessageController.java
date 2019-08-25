package com.woowacourse.dsgram.web.controller;

import com.woowacourse.dsgram.domain.InputMessage;
import com.woowacourse.dsgram.domain.OutputMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {
    @GetMapping("/dm")
    public String showDMPage() {
        return "chat";
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(InputMessage inputMessage) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(inputMessage.getFrom(), inputMessage.getText(), time);
    }
}
