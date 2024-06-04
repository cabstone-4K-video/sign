package com.example.sign.controller;

import io.openvidu.java.client.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private OpenVidu openVidu;
    private Map<String, Session> sessions;

    public SessionController(@Value("${openvidu.url}") String openViduUrl,
                             @Value("${openvidu.secret}") String openViduSecret) {
        this.openVidu = new OpenVidu(openViduUrl, openViduSecret);
        this.sessions = new ConcurrentHashMap<>();
    }

    @GetMapping("/create")
    public String createSession() {
        try {
            SessionProperties properties = new SessionProperties.Builder().build();
            Session session = openVidu.createSession(properties);
            String sessionId = session.getSessionId();
            sessions.put(sessionId, session); // 세션 ID와 세션 객체를 저장합니다.
            return sessionId;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating session";
        }
    }

    @GetMapping("/get-token")
    public String getToken(@RequestParam String sessionId) {
        try {
            Session session = sessions.get(sessionId); // 세션 객체를 맵에서 검색합니다.
            if (session == null) {
                return "Session not found";
            }

            ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                    .role(OpenViduRole.PUBLISHER)
                    .data("Alice")
                    .build();
            Connection connection = session.createConnection(connectionProperties);
            return connection.getToken();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error creating token";
        }
    }
}
