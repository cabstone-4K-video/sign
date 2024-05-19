package com.example.sign.service;

import com.example.sign.entity.dto.ChatRoomDto;
import com.example.sign.entity.dto.ChatRoomMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ChatServiceMain {

    private final MsgChatService msgChatService;
    private final RtcChatService rtcChatService;

    // 전체 채팅방 조회
    public List<ChatRoomDto> findAllRoom() {
        List<ChatRoomDto> chatRooms = new ArrayList<>(ChatRoomMap.getInstance().getChatRooms().values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // roomID 기준으로 채팅방 찾기
    public ChatRoomDto findRoomById(String roomId) {
        return ChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    // roomName 로 채팅방 만들기
    public ChatRoomDto createChatRoom(String roomName, String roomPwd, boolean secretChk, int maxUserCnt, String chatType) {
        ChatRoomDto room;
        if (chatType.equals("msgChat")) {
            room = msgChatService.createChatRoom(roomName, roomPwd, secretChk, maxUserCnt);
        } else {
            room = rtcChatService.createChatRoom(roomName, roomPwd, secretChk, maxUserCnt);
        }
        return room;
    }

    // 채팅방 비밀번호 확인
    public boolean confirmPwd(String roomId, String roomPwd) {
        return roomPwd.equals(ChatRoomMap.getInstance().getChatRooms().get(roomId));
    }

    // 채팅방 인원+1
    public void plusUserCnt(String roomId) {
        log.info("cnt {}", ChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount() + 1);
    }

    // 채팅방 인원-1
    public void minusUserCnt(String roomId) {
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount() - 1);
    }

    // maxUserCnt 에 따른 채팅방 입장 여부
    public boolean chkRoomUserCnt(String roomId) {
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        return (room.getUserCount() + 1) <= room.getMaxUserCnt();
    }

    // 채팅방 삭제
    public void delChatRoom(String roomId) {
        ChatRoomMap.getInstance().getChatRooms().remove(roomId);
        log.info("삭제 완료 roomId : {}", roomId);
    }
}
