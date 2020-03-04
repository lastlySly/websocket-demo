package cn.lastlysly.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-27 15:49
 *
 * 功能描述：springboot使用，连接监听器
 **/
@Component
public class ConnectEventListener implements ApplicationListener<SessionConnectEvent> {
    @Override
    public void onApplicationEvent(SessionConnectEvent sessionConnectEvent) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
        System.out.println("【ConnectEventListener监听器事件 类型】"+stompHeaderAccessor.getCommand().getMessageType());
    }
}
