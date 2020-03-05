package com.lastlysly.websokcet;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 16:31
 * 创建节点配置类
 **/
public class MyEndpointConfigure extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
//    private static volatile BeanFactory context;

    public static final String USER_PROPERTIES_CURRENT_USER = "currentUser";

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {

//        UserInfoView userInfoView = ApiRequestContext.get().getUserInfoView();
//        sec.getUserProperties().put("USER_PROPERTIES_CURRENT_USER",userInfoView);
        super.modifyHandshake(sec, request, response);
    }

    @Override
    public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
        //每次创建新的WebSocketSession
        return applicationContext.getAutowireCapableBeanFactory().createBean(clazz);

//        return context.getBean(clazz);

//        return super.getEndpointInstance(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MyEndpointConfigure.applicationContext = applicationContext;
    }
}
