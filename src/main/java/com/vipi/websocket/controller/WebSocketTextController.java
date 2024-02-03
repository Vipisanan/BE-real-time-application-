package com.vipi.websocket.controller;

import com.vipi.websocket.dto.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class WebSocketTextController {

    @Autowired
    SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
//        template.convertAndSend("/topic/message", textMessageDTO);
        // Correct destination path for user queue
        template.convertAndSendToUser(textMessageDTO.getUsername(), "/topic/message", textMessageDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

//    @MessageMapping("/sendMessage")
//    public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
//        // receive message from client
//    }


    @SendTo("/topic/message")
    public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
        return textMessageDTO;
    }

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    @GetMapping("/subscribers")
    public ResponseEntity<List<String>> getActiveSubscribers() {
        Set<SimpUser> users = simpUserRegistry.getUsers();
        List<String> activeSubscribers = users.stream().map(u->u.getName())
                .collect(Collectors.toList());
        return ResponseEntity.ok(activeSubscribers);
    }

}
