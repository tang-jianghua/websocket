/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.websocket.demo.model.MessageModel;
import com.websocket.demo.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    BusinessService businessService;

    /**
     * 建立连接后触发的回调
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("MyWebSocketHandler-afterConnectionEstablished-建立连接");
        logger.info("sessionId:{}-getRemoteAddress:{}",webSocketSession.getId(),webSocketSession.getRemoteAddress());
    }

    /**
     * 收到消息时触发的回调
     */
    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) throws Exception {
        String payload = textMessage.getPayload();
        logger.info("收到来自{}的消息:{}",webSocketSession.getId(),payload);
        //自定义消息格式，此处笔者采用json字符串形式。
        JSONObject jsonObject = JSON.parseObject(payload);
        Object action = jsonObject.get("action");
        if(action != null && action.toString().equals("login")){
            //登陆时，将用户信息储存在session中，便于下次使用
            webSocketSession.getAttributes().put("userId", jsonObject.get("userId"));
            webSocketSession.getAttributes().put("userName", jsonObject.get("userName"));
            businessService.addSession(webSocketSession);
            //登陆成功，反馈给客户端
            MessageModel messageModel = new MessageModel();
            messageModel.setFromUserName("服务器");
            messageModel.setMessage("登陆成功");
            webSocketSession.sendMessage(new TextMessage(JSON.toJSONString(messageModel)));
        }else if(action != null && action.toString().equals("send")){
            //发送信息，查找接受者的session
            businessService.sendMessage(webSocketSession,jsonObject.getString("toUserId"),jsonObject.getString("message"));
        }
    }



    /**
     * 传输消息出错时触发的回调
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.warn("{}传输消息出错",webSocketSession.getId());
        logger.error(throwable.getMessage(),throwable );
    }

    /**
     * 断开连接后触发的回调
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("{}断开连接",webSocketSession.getId());
        businessService.removeSession(webSocketSession.getAttributes().get("userId").toString());
    }

    /**
     * 是否处理分片消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
