package com.vipi.websocket.controller;
import com.vipi.websocket.service.ActiveSessionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final ActiveSessionsService activeSessionsService;

    @Autowired
    public SubscriberController(ActiveSessionsService activeSessionsService) {
        this.activeSessionsService = activeSessionsService;
    }

    @GetMapping
    public Map<String, String> getActiveSubscribers() {
        return activeSessionsService.getActiveSessions();
    }
}
