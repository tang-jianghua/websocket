/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.service;

import org.springframework.web.socket.WebSocketSession;

public interface BusinessService {

    void addSession(WebSocketSession session);

    void removeSession(String userId);

    void sendMessage(WebSocketSession session,String toUserId,String message) throws Exception;
}
