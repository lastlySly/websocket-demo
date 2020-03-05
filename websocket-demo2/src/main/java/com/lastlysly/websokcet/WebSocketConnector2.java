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
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-05 11:17
 **/
@ServerEndpoint(value = "/mywebsocketserve2/{userId}",configurator = MyEndpointConfigure.class)
@Component
public class WebSocketConnector2 {
    /**
     * 当前连接数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 所有连接的客户端
     * 用来存放每个客户端对应的WebSocketConnector对象
     */
    private static Map<String,WebSocketConnector2> connectors = new ConcurrentHashMap<>();
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
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session) {
        System.out.println("新客户端连入，用户id：" + userId);
        this.session = session;

        // 加入map中
        connectors.put(session.getId(),this);
        addOnlineCount(); // 在线数加1
        if(userId!=null) {
            List<String> totalPushMsgs = new ArrayList<String>();
            totalPushMsgs.add(userId+"连接成功-"+"-当前在线人数为："+getOnlineCount());
            // 获取当前登录用户，存于sessionContext
            sessionContext = new ConnectorSessionContext();
            UserInfoView userInfoView = new UserInfoView();
            userInfoView.setUserId(session.getId());
            userInfoView.setUserName("小红" + session.getId());
            sessionContext.setUser(userInfoView);
            if(totalPushMsgs != null && !totalPushMsgs.isEmpty()) {
                totalPushMsgs.forEach(e -> sendMessage(e));
            }
        }
    }
    /**
     * 连接关闭调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        String sessionId = session.getId();
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
        System.out.println("收到客户端发来的消息:" + message + "发送客户端sessionId为：" + session.getId()
                + "userId为：" + connectors.get(session.getId()).sessionContext.getUserId());
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            System.out.println("推送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketConnector2 productWebSocket : connectors.values()) {
            productWebSocket.sendMessage(message);
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
