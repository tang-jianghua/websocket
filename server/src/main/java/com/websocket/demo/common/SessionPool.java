/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.common;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class SessionPool {

    /**
     * 使用map存储管理session
     */
    public static Map<String,WebSocketSession> sessionMap = new HashMap<>();
}
