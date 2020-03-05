package com.lastlysly.websokcet.messageHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastlysly.websokcet.MyWebSocketMessage;
import com.lastlysly.websokcet.WebSocketConnector;
import org.springframework.stereotype.Component;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 17:52
 **/
@Component
public class SystemMessageHandler implements IMessageHandler{
    @Override
    public boolean handle(MyWebSocketMessage webSocketMessage) {
        return webSocketMessage.getMessageType() == MyWebSocketMessage.MessageType.systemMessage;
    }

    @Override
    public void handleMessage(WebSocketConnector connector, MyWebSocketMessage webSocketMessage) {
        System.out.println("处理系统消息");
        String[] userIds = webSocketMessage.getNotifyUserIds().toArray(new String[webSocketMessage.getNotifyUserIds().size()]);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            WebSocketConnector.sendMessage(objectMapper.writeValueAsString(webSocketMessage),userIds);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
