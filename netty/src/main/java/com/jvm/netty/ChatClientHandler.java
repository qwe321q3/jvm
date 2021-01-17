package com.jvm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天室客户端处理
 */
public class ChatClientHandler extends SimpleChannelInboundHandler {

    List<Channel> channelList = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel channel = channelHandlerContext.channel();
        ByteBuf b = (ByteBuf) o;
        System.out.println(b.toString(CharsetUtil.UTF_8).trim());
    }

}
