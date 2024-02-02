package com.vipi.websocket.config;
import com.vipi.websocket.service.ActiveSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.Objects;

@Component
public class WebSocketEventListener {

    private final ActiveSessionsService activeSessionsService;

    @Autowired
    public WebSocketEventListener(ActiveSessionsService activeSessionsService) {
        this.activeSessionsService = activeSessionsService;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        activeSessionsService.addSession(sessionId, "user");
        System.out.println("WebSocket Connect: SessionId - " + sessionId);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headers.getSessionId();
        activeSessionsService.removeSession(sessionId);
        System.out.println("WebSocket Disconnect: SessionId - " + sessionId);
    }

//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//
//        // Retrieve username from the headers
//        String userName = Objects.requireNonNull(headers.getUser()).getName();
//        System.out.println("WebSocket Connect: SessionId - " + headers.getSessionId() + ", User - " + userName);
//
//        // Construct the user-specific topic
//        String userTopic = "/user/" + userName + "/topic/message";
//
//        // Add the user-specific topic to your logic (for example, storing it in the ActiveSessionsService)
//         activeSessionsService.addUserTopic(headers.getSessionId(), userTopic);
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = headers.getSessionId();
//        System.out.println("WebSocket Disconnect: SessionId - " + sessionId);
//
//        // Optionally, remove user-specific topic from your logic (if added during connect)
//         activeSessionsService.removeUserTopic(sessionId);
//    }
}
