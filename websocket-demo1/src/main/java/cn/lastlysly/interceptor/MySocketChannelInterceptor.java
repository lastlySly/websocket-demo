package cn.lastlysly.interceptor;

import cn.lastlysly.controller.chatroom.UserLoginController;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-31 15:18
 *
 * 频道拦截器 ，类似管道，可以获取消息的一些meta数据
 **/
public class MySocketChannelInterceptor extends ChannelInterceptorAdapter {

    /**
     * 在消息被实际发送到频道之前调用
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("MySocketChannelInterceptor->preSend");
        return super.preSend(message, channel);
    }

    /**
     * 发送消息调用后立即调用
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("MySocketChannelInterceptor->postSend");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器

        if (headerAccessor.getCommand() == null ) return ;// 避免非stomp消息类型，例如心跳检测

        String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();
        System.out.println("SocketChannelIntecepter -> sessionId = "+sessionId);

        switch (headerAccessor.getCommand()) {
            case CONNECT:
                connect(sessionId);
                break;
            case DISCONNECT:
                disconnect(sessionId);
                break;
            case SUBSCRIBE:

                break;

            case UNSUBSCRIBE:

                break;
            default:
                break;
        }
    }

    /**
     * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源清理
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex) {
        System.out.println("MySocketChannelInterceptor->afterSendCompletion");
        super.afterSendCompletion(message, channel, sent, ex);
    }


    //连接成功
    private void connect(String sessionId){
        System.out.println("用户连接，connect sessionId="+sessionId);
    }


    //断开连接
    private void disconnect(String sessionId){
        System.out.println("用户断开连接，disconnect sessionId="+sessionId);
        //用户下线操作
        UserLoginController.onlineUser.remove(sessionId);
    }


}
