package cn.lastlysly.controller.singlechat;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-27 16:11
 *
 * 功能描述：简单版单人聊天
 **/
@Controller
@MessageMapping("/singlechatdemo")
public class SingleChatController {

//    public String state;

    @Autowired
    private WebSocketService webSocketService;

    @MessageMapping("/singlechat")
    public void singleChat(InMessage inMessage){

        webSocketService.singleChatdemo(inMessage);
    }
//    @MessageMapping("/singlechatstate")
//    public void singlechatstate(String str){
//        state = str;
//    }

    @MessageMapping("/mysinglechat")
    public void mySingleChat(InMessage inMessage){
        webSocketService.mySingleChat(inMessage);
    }
}
