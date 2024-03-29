package com.jvm.netty.base;

import com.jvm.model.User;
import com.jvm.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 聊天室客户端处理
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {



//        System.out.println((User)msg);
        ByteBuf in = (ByteBuf) msg;
//        System.out.println(b.toString(CharsetUtil.UTF_8));
        byte[] b = new byte[in.readableBytes()];
        in.readBytes(b);
        System.out.println(ProtostuffUtils.deserialize(b, User.class));

    }

}
