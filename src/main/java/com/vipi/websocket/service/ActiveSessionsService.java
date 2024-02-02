package com.vipi.websocket.service;

import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ActiveSessionsService {

    private final Map<String, String> activeSessions = new ConcurrentHashMap<>();

    public Map<String, String> getActiveSessions() {
        return activeSessions;
    }

    public void addSession(String sessionId, String userId) {
        activeSessions.put(sessionId, userId);
    }

    public void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }


    private final Map<String, String> userTopics = new ConcurrentHashMap<>();

    public void addUserTopic(String sessionId, String userTopic) {
        userTopics.put(sessionId, userTopic);
    }

    public String getUserTopic(String sessionId) {
        return userTopics.get(sessionId);
    }

    public void removeUserTopic(String sessionId) {
        userTopics.remove(sessionId);
    }
}
