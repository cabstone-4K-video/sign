package com.example.sign.controller;

import com.example.sign.entity.dto.ChatRoomDto;
import com.example.sign.entity.dto.ChatRoomMap;
import com.example.sign.service.ChatServiceMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    private final ChatServiceMain chatServiceMain;

    @GetMapping("/test")
    public String index() {
        return "Hi";
    }

    // 채팅방 생성
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPwd") String roomPwd,
                             @RequestParam("secretChk") String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes rttr) {
        ChatRoomDto room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);
        log.info("CREATE Chat Room [{}]", room);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    @GetMapping("/chat/room")
    public String roomDetail(Model model, @RequestParam String roomId) {
        log.info("roomId {}", roomId);
        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);
        model.addAttribute("room", room);  // 여기에서 addAttribute 사용

        if (ChatRoomDto.ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        } else {
            model.addAttribute("uuid", UUID.randomUUID().toString());
            return "rtcroom";
        }
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/chat/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd) {
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/chat/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId) {
        chatServiceMain.delChatRoom(roomId);
        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chat/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chkUserCnt(@PathVariable String roomId) {
        return chatServiceMain.chkRoomUserCnt(roomId);
    }
}
