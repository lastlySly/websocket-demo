package cn.lastlysly.controller.singlechatdemo;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-06-01 18:21
 **/
@Controller
public class SingleChatDemoController {
    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/singlemessage")
    public void singleChatdemo(InMessage inMessage){
        System.out.println(inMessage.getFrom()+"===="+inMessage.getContent()+"===="+inMessage.getTo());
        webSocketService.singleChatdemo(inMessage);
    }
}
