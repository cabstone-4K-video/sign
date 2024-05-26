package com.example.sign.controller;

import com.example.sign.entity.dto.ChatRoomDto;
import com.example.sign.entity.dto.ChatRoomMap;
import com.example.sign.entity.dto.ChatType;
import com.example.sign.service.ChatServiceMain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {

    // ChatService Bean 가져오기
    private final ChatServiceMain chatServiceMain;

    // 채팅방 생성
    // 채팅방 생성 후 다시 / 로 return
    @PostMapping("/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("userName") String userName,
                             @RequestParam(value = "roomPwd", required = false) String roomPwd,
                             @RequestParam(value = "secretChk", required = false) String secretChk,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2", required = false) String maxUserCnt,
                             @RequestParam(value = "chatType", required = false) String chatType,
                             RedirectAttributes rttr) {

        // log.info("chk {}", secretChk);

        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        ChatRoomDto room;

        room = chatServiceMain.createChatRoom(name, roomPwd, Boolean.parseBoolean(secretChk), Integer.parseInt(maxUserCnt), chatType);

        log.info("CREATE Chat Room [{}]", room);

        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    // 채팅방 입장 화면
    // 파라미터로 넘어오는 roomId 를 확인후 해당 roomId 를 기준으로
    // 채팅방을 찾아서 클라이언트를 chatroom 으로 보낸다.
    @GetMapping("/room")
    public String roomDetail(Model model, @RequestParam String roomId) {

        log.info("roomId {}", roomId);

        ChatRoomDto room = ChatRoomMap.getInstance().getChatRooms().get(roomId);

        model.addAttribute("room", room);


        if (ChatType.MSG.equals(room.getChatType())) {
            return "chatroom";
        } else {
            String uuid = UUID.randomUUID().toString().split("-")[0];
            model.addAttribute("uuid", uuid);
            model.addAttribute("roomId", room.getRoomId());
            model.addAttribute("roomName", room.getRoomName());
//            return "rtcroom";

            return "kurentoroom";
        }
    }

    // 채팅방 비밀번호 확인
    @PostMapping("/confirmPwd/{roomId}")
    @ResponseBody
    public boolean confirmPwd(@PathVariable String roomId, @RequestParam String roomPwd) {

        // 넘어온 roomId 와 roomPwd 를 이용해서 비밀번호 찾기
        // 찾아서 입력받은 roomPwd 와 room pwd 와 비교해서 맞으면 true, 아니면  false
        return chatServiceMain.confirmPwd(roomId, roomPwd);
    }

    // 채팅방 삭제
    @GetMapping("/delRoom/{roomId}")
    public String delChatRoom(@PathVariable String roomId) {

        // roomId 기준으로 chatRoomMap 에서 삭제, 해당 채팅룸 안에 있는 사진 삭제
        chatServiceMain.delChatRoom(roomId);

        return "redirect:/";
    }

    // 유저 카운트
    @GetMapping("/chkUserCnt/{roomId}")
    @ResponseBody
    public boolean chUserCnt(@PathVariable String roomId) {

        return chatServiceMain.chkRoomUserCnt(roomId);
    }
}