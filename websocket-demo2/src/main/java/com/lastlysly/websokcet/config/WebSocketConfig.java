package com.lastlysly.websokcet.config;

import com.lastlysly.websokcet.MyEndpointConfigure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 15:25
 * WebSocket配置类
 **/
@Configuration
public class WebSocketConfig {
    /**首先在该类中注入一个ServerEndpointExporter的bean,
     *ServerEndpointExporter这个bean会自动注册使用了@ServerEndpoint这个注解的websocket endpoint（需要交给spring管理，注册为bean，否则需要加入
     * serverEndpointExporter.setAnnotatedEndpointClasses(WebSocketConnector.class);来指定）
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
//        如果WebSocketConnector没有交由spring管理，则需要加入以下这句指定，
//        serverEndpointExporter.setAnnotatedEndpointClasses(WebSocketConnector.class);
        return serverEndpointExporter;
    }
    @Bean
    public MyEndpointConfigure myEndpointConfigure() {
        return new MyEndpointConfigure();
    }
}
