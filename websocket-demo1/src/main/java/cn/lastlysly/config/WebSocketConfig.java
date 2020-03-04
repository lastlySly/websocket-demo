package cn.lastlysly.config;

import cn.lastlysly.interceptor.MyHttpHandShakeInterceptor;
import cn.lastlysly.interceptor.MySocketChannelInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-26 15:30
 * @EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
 **/
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册端点，发布或者订阅消息的时候需要连接此端点
     * setAllowedOrigins 非必须，*表示允许其他域进行连接
     * withSockJS  表示开启sockejs支持
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * .registry.addEndpoint("/endpointOyzc").setAllowedOrigins("*").withSockJS(); 添加一个访问端点“/endpointGym”,
         * 客户端打开双通道时需要的url,允许所有的域名跨域访问，指定使用SockJS协议
         */
        registry.addEndpoint("/endpoint-websocket").addInterceptors(new MyHttpHandShakeInterceptor()).setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理(中介)
     * enableSimpleBroker 服务端推送给客户端的路径前缀
     * setApplicationDestinationPrefixes  客户端发送数据给服务器端的一个前缀
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/chat","/mysystem","/chatroom");
        registry.setApplicationDestinationPrefixes("/app");

        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
         registry.setUserDestinationPrefix("/user/");

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MySocketChannelInterceptor());
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new MySocketChannelInterceptor());
    }
}

/**
 * websocket 注解介绍
 *     1. @MessageMapping
 *
 *     @MessageMapping，用户处理client发送过来的消息，被注解的方法可以具有以下参数。 
 *
 *          Message：用于接收完整的消息
 *          MessageHeaders：用于接收消息中的头信息
 *          MessageHeaderAccessor/SimpMessageHeaderAccessor/StompHeaderAccessor：用于接收消息中的头信息，并且构建绑定Spring中的一些附加信息
 *          @Headers：用于接收消息中的所有header。这个参数必须用java.util.Map
 *          @Header：用于接收特定的头值
 *          @Payload：接受STOMP协议中的Body，可以用@javax.validation进行注释, Spring的@Validated自动验证 （类似@RequestBody）
 *          DestinationVariable: 用于提取header中destination模板变量 （类似@PathVariable）
 *          java.security.Principal：接收在WebSocket HTTP握手时登录的用户
 *         当@MessageMapping方法返回一个值时，默认情况下，该值在被序列化为Payload后，作为消息发送到向订阅者广播的“brokerChannel”，且消息destination与接收destination相同，但前缀为变为配置的值。
 *
 *         可以使用@SendTo指定发送的destination，将Payload消息，进行广播发送到订阅的客户端。@SendToUser是会向与当条消息关联的用户发送回执消息，还可以使用SimpMessagingTemplate发送代替SendTo/@SendToUserji进行消息的发送
 *
 *     2. @SubscribeMapping 
 *
 *         @SubscribeMapping注释与@MessageMapping结合使用，以缩小到订阅消息的映射。在这种情况下，@MessageMapping注释指定目标，而@SubscribeMapping仅表示对订阅消息的兴趣。
 *
 *         @SubscribeMapping通常与@MessageMapping没有区别。关键区别在于，@SubscribeMapping的方法的返回值被序列化后，会发送到“clientOutboundChannel”，而不是“brokerChannel”，直接回复到客户端，而不是通过代理进行广播。这对于实现一次性的、请求-应答消息交换非常有用，并且从不占用订阅。这种模式的常见场景是当数据必须加载和呈现时应用程序初始化。
 *
 *         @SendTo注释@SubscribeMapping方法，在这种情况下，返回值被发送到带有显式指定目标目的地的“brokerChannel”。
 *
 *     3. @MessageExceptionHandler
 *
 *        应用程序可以使用@MessageExceptionHandler方法来处理@MessageMapping方法中的异常。        @MessageExceptionHandler方法支持灵活的方法签名，并支持与@MessageMapping方法相同的方法参数类型和返回值。与Spring MVC中的@ExceptionHandler类似。
 */