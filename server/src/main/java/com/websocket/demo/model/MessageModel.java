/*
 * Copyright (c) 2019.  唐江华 All rights reserved
 */

package com.websocket.demo.model;

public class MessageModel {

    /**
     * 发信人id
     */
    private String fromUserId;

    /**
     * 发信人名称
     */
    private String fromUserName;

    /**
     * 信息内容
     */
    private String message;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
