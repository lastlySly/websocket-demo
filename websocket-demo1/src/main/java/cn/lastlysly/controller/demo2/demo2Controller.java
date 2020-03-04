package cn.lastlysly.controller.demo2;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-26 17:03
 *
 *
 * 1、SendTo 不通用，固定发送给指定的订阅者
 * 2、SimpMessagingTemplate 灵活，支持多种发送方式
 * SimpMessagingTemplate 灵活，支持多种发送方式
 * 使用SimpMessagingTemplate完成游戏公告
 **/
@Controller
public class demo2Controller {

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/v2/chat")
    public void gameInfo(InMessage message) throws InterruptedException {
//        String cont = request.notify();
//        System.out.println("============" + cont);

        System.out.println(message.toString());
        webSocketService.sendTopicMessage("/topic/game_rank",message);
    }
//    @MessageMapping("/v2/chat")
//    public void gameInfo(String message) throws InterruptedException {
////        String cont = request.notify();
////        System.out.println("============" + cont);
//
//        System.out.println(message.toString());
////        webSocketService.sendTopicMessage("/topic/game_rank",message);
//    }

}
