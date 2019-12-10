/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.config;

import com.websocket.demo.handler.MyWebSocketHandler;
import com.websocket.demo.interceptor.MyWebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    MyWebSocketInterceptor myWebSocketInterceptor;
    @Autowired
    MyWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //监听请求url
        webSocketHandlerRegistry.addHandler(handler,"/demo")
                //处理跨域
                .setAllowedOrigins("*")
                //添加拦截器
                .addInterceptors(myWebSocketInterceptor);
    }
}
