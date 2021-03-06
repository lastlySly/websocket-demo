package cn.lastlysly.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-28 15:58
 *
 * http握手拦截器，可以通过这个类的方法获取request和response
 * 这是springboot与websocket的拦截器，配置定义在websocket配置文件里
 **/
public class MyHttpHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) throws Exception {

        System.out.println("【握手拦截器】beforeHandshake");

        // (a instanceof b )a是否为b的一个实例
        if (serverHttpRequest instanceof ServerHttpRequest){
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
            String sessionId = session.getId();
            System.out.println("【握手拦截器】beforeHandshake sessionId="+sessionId);
            map.put("sessionId", sessionId);
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                               ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
                               @Nullable Exception e) {
        System.out.println("【握手拦截器】afterHandshake");
        if(serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)serverHttpRequest;
            HttpSession session =  servletRequest.getServletRequest().getSession();
            String sessionId = session.getId();
            System.out.println("【握手拦截器】afterHandshake sessionId="+sessionId);
        }

    }
}
