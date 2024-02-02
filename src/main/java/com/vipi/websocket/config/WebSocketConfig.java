package com.vipi.websocket.config;

import com.vipi.websocket.service.ActiveSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ActiveSessionsService activeSessionsService;
    @Autowired
    public WebSocketConfig(ActiveSessionsService activeSessionsService) {
        this.activeSessionsService = activeSessionsService;
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/user");
        config.setUserDestinationPrefix("/user"); // Include username in destination
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // with sockjs
//        registry.addEndpoint("/ws-message")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
        // without sockjs
        registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*");
    }

    @Bean
    public WebSocketEventListener eventListener() {
        return new WebSocketEventListener(activeSessionsService);
    }
}
