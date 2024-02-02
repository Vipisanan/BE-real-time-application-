package com.vipi.websocket.config;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);

        // Extract username from the destination path
        String destination = accessor.getDestination();
        String username = "N/A";
        if(destination != null)
            username=extractUsernameFromDestination(destination);

        // Log subscriber's path
        String sessionId = accessor.getSessionId();

        System.out.println("Subscriber Path - Destination: " + destination + ", SessionId: " + sessionId + ", Username: " + username);

        return message;
    }

    private String extractUsernameFromDestination(String destination) {
        // Extract username from the destination path (assuming the path structure is known)
        String[] pathSegments = destination.split("/");
        if (pathSegments.length >= 3 && pathSegments[1].equals("user")) {
            return pathSegments[2];
        } else {
            return "N/A";
        }
    }
}

