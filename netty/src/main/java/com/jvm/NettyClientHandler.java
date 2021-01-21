package com.jvm;

import com.jvm.model.User;
import com.jvm.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 聊天室客户端处理
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);


//        System.out.println((User)msg);
//        ByteBuf b = (ByteBuf) msg;
//        System.out.println(b.toString(CharsetUtil.UTF_8));
        System.out.println(ProtostuffUtils.deserialize((byte[])msg, User.class));

    }

}
