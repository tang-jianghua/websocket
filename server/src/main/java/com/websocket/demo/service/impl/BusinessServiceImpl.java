/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.websocket.demo.common.SessionPool;
import com.websocket.demo.model.MessageModel;
import com.websocket.demo.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;


@Service
public class BusinessServiceImpl implements BusinessService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void addSession(WebSocketSession session) {
        String userId = session.getAttributes().get("userId").toString();
        if(!SessionPool.sessionMap.containsKey(userId)){
            SessionPool.sessionMap.put(userId, session);
        }
    }

    @Override
    public void removeSession(String userId) {
        SessionPool.sessionMap.remove(userId);
    }

    @Override
    public void sendMessage(WebSocketSession session,String toUserId, String message) throws Exception{
        String userId = session.getAttributes().get("userId").toString();
        String userName = session.getAttributes().get("userName").toString();

        if(!SessionPool.sessionMap.containsKey(toUserId)){
            session.sendMessage(new TextMessage("对方处于离线状态！请稍后再发。"));
            return;
        }
        WebSocketSession webSocketSession = SessionPool.sessionMap.get(toUserId);
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);
        messageModel.setFromUserId(userId);
        messageModel.setFromUserName(userName);
        webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(messageModel)));
    }
}
