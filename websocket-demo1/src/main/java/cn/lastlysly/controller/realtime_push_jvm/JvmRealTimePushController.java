package cn.lastlysly.controller.realtime_push_jvm;

import cn.lastlysly.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-27 17:36
 *
 * 实时推送服务器的JVM负载，已用内存等消息
 **/
@Controller
public class JvmRealTimePushController {

    @Autowired
    private WebSocketService webSocketService;

    @Scheduled(fixedRate = 3000)  //用@Scheduled的方法不能加参数
    public void sendServerInfo(){
        webSocketService.sendServerInfo();
    }
}
