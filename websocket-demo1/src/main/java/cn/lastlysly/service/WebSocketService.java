package cn.lastlysly.service;

import cn.lastlysly.pojo.InMessage;
import cn.lastlysly.pojo.OutMessage;
import cn.lastlysly.pojo.UserSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-26 17:04
 * 简单消息模板，用来推送消息
 **/
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 游戏公告
     * @param dec
     * @param inMessage
     * @throws InterruptedException
     */
    public void sendTopicMessage(String dec, InMessage inMessage) throws InterruptedException {
        for (int i=0;i<10;i++){
            Thread.sleep(1000L);
            simpMessagingTemplate.convertAndSend(dec,new OutMessage(inMessage.getContent()+i));
        }
    }

    /**
     * 简单单点聊天
     * @param inMessage
     */
    public void singleChatdemo(InMessage inMessage) {
        simpMessagingTemplate.convertAndSend("/chat/single/"+inMessage.getTo(),
                new OutMessage(inMessage.getFrom()+" 发送:"+ inMessage.getContent()));
    }

    /**
     * 测试convertAndSendToUser这个方法
     ** @param inMessage
     */
    public void mySingleChat(InMessage inMessage) {
        System.out.println(inMessage.getTo()+"=====----");
        simpMessagingTemplate.convertAndSendToUser(inMessage.getTo(),"/chat/single",
                new OutMessage(inMessage.getFrom()+" 发送:"+ inMessage.getContent()));
    }
 /**
     * 测试convertAndSendToUser这个方法
     ** @param inMessage
     */
    public void singleChatDemo(InMessage inMessage) {
        System.out.println(inMessage.getTo()+"=====----");
        simpMessagingTemplate.convertAndSendToUser(inMessage.getTo(),"/message",
                new OutMessage(inMessage.getFrom()+" 发送:"+ inMessage.getContent()));
    }

    /**
     * 服务端定时推送服务器的JVM负载，已用内存等消息
     */
    public void sendServerInfo() {
        int processors = Runtime.getRuntime().availableProcessors();
        Long freeMem = Runtime.getRuntime().freeMemory();

        Long maxMem = Runtime.getRuntime().maxMemory();

        String message = String.format("服务器可用处理器:%s; 虚拟机空闲内容大小: %s; 最大内存大小: %s", processors,freeMem,maxMem );

        simpMessagingTemplate.convertAndSend("/mysystem/server_info",new OutMessage(message));
    }


    /**
     * 发送在线用户
     * @param onlineUser
     */
    public void sendOnlineUser(Map<String, UserSheet> onlineUser) {
        String msg = "";
        for(Map.Entry<String, UserSheet> entry : onlineUser.entrySet()){
            msg = msg.concat(entry.getValue().getUsername()+"||");
        }
        System.out.println(msg);
        simpMessagingTemplate.convertAndSend("/chatroom/onlineuser",new OutMessage(msg));
    }

    /**
     * 用于多人聊天
     * @param message
     */
    public void sendTopicChat(InMessage message) {
        String msg = message.getFrom() +" 发送:"+message.getContent();
        simpMessagingTemplate.convertAndSend("/chatroom/chat",new OutMessage(msg));
    }
}
