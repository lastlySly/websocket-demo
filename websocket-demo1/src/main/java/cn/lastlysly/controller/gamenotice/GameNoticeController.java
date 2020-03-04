package cn.lastlysly.controller.gamenotice;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.pojo.OutMessage;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-26 15:53
 *
 * 1、SendTo 不通用，固定发送给指定的订阅者
 * 2、SimpMessagingTemplate 灵活，支持多种发送方式
 *
 * 使用@SendTo 完成游戏公告
 **/
@Controller
public class GameNoticeController {

    @MessageMapping("/v1/chat")
    @SendTo("/topic/game_chat")
    public OutMessage gameInfo(InMessage message, String con,String tes,SimpMessageHeaderAccessor headerAccessor){
        String ss = con;
        System.out.println(tes + "===" + ss);
        System.out.println("===============" + headerAccessor.toString());
        System.out.println("GameInfoController->gameInfo");
        return new OutMessage(message.getContent());
    }
}
