package com.lastlysly.websokcet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lastlysly.view.UserInfoView;
import com.lastlysly.websokcet.messageHandler.IMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 15:53
 **/
@ServerEndpoint(value = "/mywebsocketserve",configurator = MyEndpointConfigure.class)
@Component
public class WebSocketConnector {
    /**
     * 当前连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 所有连接的客户端
     * 用来存放每个客户端对应的WebSocketConnector对象
     */
    private static Map<String,WebSocketConnector> connectors = new ConcurrentHashMap<>();
    /**
     * 所有连接的session
     */
//    private static Map<String,Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * 当前连接的websocket会话
     *  与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;
    /**
     * 上下文
     */
    private ConnectorSessionContext sessionContext;

    /**
     * 所有实现IMessageHandler接口并且交由spring管理的实现类实例集合。
     */
    @Autowired
    private List<IMessageHandler> messageHandlers;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 连接成功调用的方法
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        this.session =session;
        sessionContext = new ConnectorSessionContext();

        // 获取当前登录用户，存于sessionContext
        UserInfoView userInfoView = new UserInfoView();
        userInfoView.setUserId(session.getId());
        userInfoView.setUserName("小明" + session.getId());
        sessionContext.setUser(userInfoView);

        int count = addOnlineCount();
        connectors.put(session.getId(), this);
        System.out.println("有新的客户端上线sessionId为: " + session.getId() + "，当前在线数：" + count);
    }
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(@PathParam("userId")String userId, Session session) {
//        System.out.println("新客户端连入，用户id：" + userId);
//        this.session = session;
//        // 加入map中
//        connectors.put(session.getId(),this);
//        addOnlineCount(); // 在线数加1
//        if(userId!=null) {
//            List<String> totalPushMsgs = new ArrayList<String>();
//            totalPushMsgs.add(userId+"连接成功-"+"-当前在线人数为："+getOnlineCount());
//
//            if(totalPushMsgs != null && !totalPushMsgs.isEmpty()) {
//                totalPushMsgs.forEach(e -> sendMessage(e));
//            }
//        }
//    }
    /**
     * 连接关闭调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
//        sessionMap.remove(sessionId);
        if (connectors.containsKey(this.session.getId())) {
            connectors.remove(this.session.getId());
            subOnlineCount();
        }
        System.out.println("有客户端离线: " +  sessionId);
    }

    /**
     * 发生错误时调用
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
//        if (sessionMap.get(session.getId()) != null) {
//            sessionMap.remove(session.getId());
//        }
        if (connectors.get(session.getId()) != null) {
            connectors.remove(session.getId());
        }
        System.out.println("有客户端连接websocket错误");
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        System.out.println("收到客户端发来的消息:" + message + "发送人为：" + session.getId());
        MyWebSocketMessage webSocketMessage = null;
        try {
            webSocketMessage = objectMapper.readValue(message,MyWebSocketMessage.class);
        } catch (JsonProcessingException e) {
            System.out.println("消息序列化失败");
            e.printStackTrace();
            return;
        }
        /**
         * 消息处理：如满足指定类型发送到指定位置等。
         */
        if (messageHandlers != null && messageHandlers.size() > 0) {
            for (IMessageHandler messageHandler : messageHandlers) {
                if (messageHandler.handle(webSocketMessage)) {
                    messageHandler.handleMessage(this,webSocketMessage);
                }
            }
        }
//        this.sendTo(gson.fromJson(message, Message.class));
    }

    /**
     * 给当前连接用户发送消息
     * @param message
     */
    public void sendMessage(String message) {
        if (this.session != null) {
            this.session.getAsyncRemote().sendText(message);
//            this.session.getBasicRemote().sendText(message);
        } else {
            System.out.println(sessionContext.getUser() + "未连接websocket，发送消息失败");

        }
    }
    public void sendMessage(Object object) {
        if (object != null) {
            try {
                String message = object instanceof String ? object.toString() : objectMapper.writeValueAsString(object);
                sendMessage(message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 给指定用户发消息，发送给指定sessionId的客户端
     * @param message
     * @param userIds
     */
    public static void sendMessage(String message,String... userIds) {
        HashSet<String> userIdSet = new HashSet<String>(Arrays.asList(userIds));
        for (WebSocketConnector webSocketConnector : connectors.values()) {
            if (userIdSet.contains(webSocketConnector.sessionContext.getUserId())) {
                webSocketConnector.sendMessage(message);
            }
        }
    }

    /**
     * 广播消息，给所有Websocket在线用户发消息
     * @param message
     */
    public static void broadcast(String message) {
        for (WebSocketConnector webSocketConnector : connectors.values()) {
            webSocketConnector.sendMessage(message);
        }
    }

    /**
     * 获取在线数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 在线数+1，并获取
     */
    public static synchronized int addOnlineCount() {
        // 在线数加1
        return onlineCount.incrementAndGet();
    }

    /**
     * 在线数-1
     */
    public static synchronized int subOnlineCount() {
        // 在线数加1
        return onlineCount.decrementAndGet();
    }
    /**
     * 观察连接实例有被GC回收
     */
    @Override
    protected void finalize() {
        System.out.println("finalized WebsocketConnector, session " + (session == null ? "" : session.getId()));
    }

}
