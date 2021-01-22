package com.jvm.netty.codec;

/**
 * @ClassName : MessageProtocol
 * @Description : 消息协议类
 * @Author : tianshuo
 * @Date: 2021-01-22 17:14
 */
public class MessageProtocol {

    private int len;

    private String body;

    public MessageProtocol(int len, String body) {
        this.len = len;
        this.body = body;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

