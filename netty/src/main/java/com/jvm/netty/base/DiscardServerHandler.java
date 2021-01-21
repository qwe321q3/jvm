package com.jvm.netty.base;

import com.jvm.model.User;
import com.jvm.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("connection ..");
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("read complete ..");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println("aaa");
            // ByteBuf
                    ByteBuf in = (ByteBuf) msg;
//            System.out.println(in.toString(CharsetUtil.UTF_8));
//            ByteBuf resp = Unpooled.copiedBuffer("msg back success".getBytes()); // 5

            // String
//            System.out.println((String) msg);

//            System.out.println((User)msg);


            byte[] b = new byte[in.readableBytes()];
            in.readBytes(b);
            // protosuff解析
            System.out.println(ProtostuffUtils.deserialize(b,User.class));
            ctx.writeAndFlush(Unpooled.copiedBuffer(ProtostuffUtils.serialize(new User("1","server：ok"))));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
