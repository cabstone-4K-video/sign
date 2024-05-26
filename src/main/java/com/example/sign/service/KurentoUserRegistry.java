package com.example.sign.service;

import com.example.sign.handler.KurentoUserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Map of users registered in the system. This class has a concurrent hash map to store users, using
 * its name as key in the map.
 * <p>
 * 유저를 관리하는 클래스로 concurrent hash map 을 쓰는데 유저명을 key 로 사용함
 *
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @author Micael Gallego (micael.gallego@gmail.com)
 * @author Ivan Gracia (izanmail@gmail.com)
 * @modifyBy SeJon Jang (wkdtpwhs@gmail.com)
 */
@Service
@RequiredArgsConstructor
public class KurentoUserRegistry {

    /**
     * @Desc 유저명 - userSession 객체 저장 map
     */
    private final ConcurrentHashMap<String, KurentoUserSession> usersByName = new ConcurrentHashMap<>();

    /**
     * @Desc 세션아이디 - userSession 객체 저장 map
     */
    private final ConcurrentHashMap<String, KurentoUserSession> usersBySessionId = new ConcurrentHashMap<>();

    /**
     * @Desc userSession 을 파라미터로 받은 후 해당 객체에서 userName 과 sessionId 를 key 로해서 userSession 저장
     * @Param userSession
     */
    public void register(KurentoUserSession user) {
        usersByName.put(user.getName(), user);
        usersBySessionId.put(user.getSession().getId(), user);
    }

    /**
     * @Desc 유저명으로 userSession 을 가져옴
     * @Param userName
     * @Return userSession
     */
    public KurentoUserSession getByName(String name) {
        return usersByName.get(name);
    }

    /**
     * @Desc 파라미터로 받은 webSocketSession 로 userSession 을 가져옴
     * @Param WebSocketSession
     * @Return userSession
     */
    public KurentoUserSession getBySession(WebSocketSession session) {
        return usersBySessionId.get(session.getId());
    }

    /**
     * @Desc 파라미터로 받은 userName 이 usersByName map 에 있는지 검색
     * @Param String userName
     * @Return Boolean
     */
    public boolean exists(String name) {
        return usersByName.keySet().contains(name);
    }

    /**
     * @return userSession 객체
     * @Desc 파라미터로 WebSocketSession 을 받은 후 이를 기준으로 해당 유저의 userSession 객체를 가져옴,
     * 이후 userByName 과 userBySessionId 에서 해당 유저를 삭제함
     * @Param WebSocketSession session
     */
    public KurentoUserSession removeBySession(WebSocketSession session) {
        final KurentoUserSession user = getBySession(session);
        usersByName.remove(user.getName());
        usersBySessionId.remove(session.getId());
        return user;
    }

}