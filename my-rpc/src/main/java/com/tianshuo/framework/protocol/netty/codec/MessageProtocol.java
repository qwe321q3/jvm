package com.tianshuo.framework.protocol.netty.codec;

import java.util.Arrays;

/**
 * @ClassName : MessageProtocol
 * @Description : 消息协议类
 * @Author : tianshuo
 * @Date: 2021-01-22 17:14
 */
public class MessageProtocol {

    private int len;

    private byte[] body;

    public MessageProtocol() {
    }

    public MessageProtocol(int len, byte[] body) {
        this.len = len;
        this.body = body;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageProtocol{" +
                "len=" + len +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}

