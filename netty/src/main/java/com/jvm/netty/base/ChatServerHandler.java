package com.jvm.netty.base;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 聊天室服务端处理
 */
public class ChatServerHandler extends SimpleChannelInboundHandler {

    public static ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);


    /**
     * 服务端轮询推送信息
     *
     * @param channelHandlerContext
     * @param o
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel channel = channelHandlerContext.channel();
        String msg = (String) o;

//        ByteBuf b = (ByteBuf) o;
        CHANNEL_GROUP.forEach(ch -> {
            if (ch != channel) {
                ch.writeAndFlush("[客户端" + channel.remoteAddress() + "]: " + msg + " " + new Date().toLocaleString());
            } else {
                ch.writeAndFlush("[自己" + channel.remoteAddress() + "]: " + msg + " " + new Date().toLocaleString());
            }
        });
    }

    /**
     * 服务端连接之后，提示上线成功
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端：" + channel.remoteAddress() + "上线成功！");
        CHANNEL_GROUP.writeAndFlush("客户端：" + channel.remoteAddress() + "上线成功！");
        CHANNEL_GROUP.add(channel);
//        channel.writeAndFlush("客户端："+channel.remoteAddress()+"上线成功！");

//        System.out.println("客户端："+channel.remoteAddress()+"上线成功！");
        super.channelActive(ctx);
    }
}
