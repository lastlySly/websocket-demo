package com.lastlysly.websokcet;

import java.io.Serializable;
import java.util.Set;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 16:17
 * 自定义websocket消息体
 **/
public class MyWebSocketMessage implements Serializable {
    /**
     * 动作
     */
    private Action action;
    /**
     * 消息类型
     */
    private MessageType messageType;
    /**
     * 指定通知的用户。（可用于特殊操作，如果群发中在前端进行过滤使用，或者作为特殊群体，被QQ的@某某人）
     */
    private Set<String> notifyUserIds;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送者
     */
    private String sendFrom;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Set<String> getNotifyUserIds() {
        return notifyUserIds;
    }

    public void setNotifyUserIds(Set<String> notifyUserIds) {
        this.notifyUserIds = notifyUserIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    /**
     * 动作
     */
    public enum Action {
        Subscribe, // 订阅
        Unsubscribe, //取消订阅
        userChatMessage, // 用户聊天消息
        systemMessage, // 系统消息
    }

    /**
     * 消息类型枚举
     */
    public enum MessageType {
        singleChat,
        systemMessage,
    }
}
