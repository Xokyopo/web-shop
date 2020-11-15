package ru.geekbrains.coursework.webshop.app.external.stomp.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.geekbrains.coursework.webshop.app.domain.entities.ChatMessage;

import java.util.Optional;

@Controller
public class ChatController {

    @MessageMapping("/chat/in")
    @SendTo("/chat/out_all")
    public ChatMessage echoServer(ChatMessage chatMessage, StompHeaderAccessor stompHeaderAccessor) {
        String sessionName = Optional.ofNullable(stompHeaderAccessor.getLogin()).orElse(stompHeaderAccessor.getSessionId());
        chatMessage.setAuthor(sessionName);
        return chatMessage;
    }
}
