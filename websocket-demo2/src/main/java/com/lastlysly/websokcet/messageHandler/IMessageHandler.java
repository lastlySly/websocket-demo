package com.lastlysly.websokcet.messageHandler;


import com.lastlysly.websokcet.MyWebSocketMessage;
import com.lastlysly.websokcet.WebSocketConnector;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 16:14
 * Websocket消息处理。客户端发送服务端，服务端收到消息后进行处理。（需对消息进行过滤或者分类处理时使用），如根据消息类型会有不同的处理方式时。
 *
 **/
public interface IMessageHandler {
    /**
     * 是否处理
     * @param webSocketMessage
     * @return
     */
    boolean handle(MyWebSocketMessage webSocketMessage);

    /**
     * 处理动作
     * @param connector
     * @param webSocketMessage
     */
    void handleMessage(WebSocketConnector connector,MyWebSocketMessage webSocketMessage);
}
