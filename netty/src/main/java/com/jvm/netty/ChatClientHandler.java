package com.jvm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 聊天室客户端处理
 */
public class ChatClientHandler extends SimpleChannelInboundHandler {


    /**
     * 读取消息
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.err.println("client read ..");

//        ByteBuf b = (ByteBuf) o;
        System.out.println((String)o);
    }

    /**
     * 连接之后，监控输入
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("client connection ..");
        super.channelActive(ctx);
    }
}
