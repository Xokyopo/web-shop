package ru.geekbrains.coursework.webshop.app.external.stomp.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.geekbrains.coursework.webshop.app.domain.entities.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/chat/in")
    @SendTo("/chat/out_all")
    public ChatMessage echoServer(ChatMessage chatMessage, StompHeaderAccessor stompHeaderAccessor) {
        String sessionName = stompHeaderAccessor.getUser() != null ?
                stompHeaderAccessor.getUser().getName() : stompHeaderAccessor.getSessionId();
        chatMessage.setAuthor(sessionName);
        return chatMessage;
    }
}
