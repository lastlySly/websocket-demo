package cn.lastlysly.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-27 15:50
 * 功能描述：springboot使用，订阅的监听器
 **/
@Component
public class SubscribeEventListener implements ApplicationListener<SessionSubscribeEvent> {
    /**
     * 在事件触发的时候调用这个方法
     *
     * StompHeaderAccessor  简单消息传递协议中处理消息头的基类，
     * 通过这个类，可以获取消息类型(例如:发布订阅，建立连接断开连接)，会话id等
     *
     */

    @Override
    public void onApplicationEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        StompHeaderAccessor headerAccessor =  StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        System.out.println("【SubscribeEventListener监听器事件 类型】"+headerAccessor.getCommand().getMessageType());
        System.out.println("【headerAccessor.getSessionId()和拦截器里获得的客户端sessionId不同】" + headerAccessor.getSessionId());
        System.out.println("如果没有在握手拦截器中获取session的id," +
                "则headerAccessor.getSessionAttributes().get(\"sessionId\")这个方法获取到的sessionId为空" +
                "【SubscribeEventListener监听器事件 sessionId】"
                +headerAccessor.getSessionAttributes().get("sessionId"));
    }
}
