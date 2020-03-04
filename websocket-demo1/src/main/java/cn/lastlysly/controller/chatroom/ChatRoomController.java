package cn.lastlysly.controller.chatroom;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.pojo.UserSheet;
import cn.lastlysly.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import static cn.lastlysly.controller.chatroom.UserLoginController.onlineUser;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-28 17:18
 **/
@Controller
public class ChatRoomController {

    @Autowired
    private WebSocketService webSocketService;


    /**
     * 用于定时给客户端推送在线用户
     */
    @Scheduled(fixedRate = 2000)
    public void onlineUser() {

        webSocketService.sendOnlineUser(onlineUser);
    }


    /**
     * 聊天接口
     * @param message 消息体
     * @param headerAccessor 消息头访问器，通过这个获取sessionId
     */
    @MessageMapping("/chatroomcontroller/mychat")
    public void topicChat(InMessage message, SimpMessageHeaderAccessor headerAccessor){
        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        UserSheet user = onlineUser.get(sessionId);
        message.setFrom(user.getUsername());
        webSocketService.sendTopicChat(message);

    }

}
