package com.jvm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天室服务端处理
 */
public class ChatServerHandler extends SimpleChannelInboundHandler {

    List<Channel> channelList = new ArrayList<>();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        Channel channel = channelHandlerContext.channel();
        ByteBuf b = (ByteBuf) o;
        channelList.forEach(ch->{
            if (ch != channel) {
                System.out.println("[客户端"+channel.remoteAddress()+"]: "+b.toString(CharsetUtil.UTF_8));
            }else{
                System.out.println("[自己"+channel.remoteAddress()+"]: "+b.toString(CharsetUtil.UTF_8));
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelList.forEach(ch-> channel.writeAndFlush("客户端："+channel.remoteAddress()+"上线成功！"));

//        System.out.println("客户端："+channel.remoteAddress()+"上线成功！");
        channelList.add(channel);
        super.channelActive(ctx);
    }
}
