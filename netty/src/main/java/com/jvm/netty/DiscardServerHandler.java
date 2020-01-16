package com.jvm.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.nio.ByteBuffer;

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
//        super.channelRead(ctx, msg);

        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println(in.toString(CharsetUtil.UTF_8));

            ByteBuf resp = Unpooled.copiedBuffer("msg back success".getBytes()); // 5


            ctx.writeAndFlush(resp);
//            in.writeBytes("msg back success ..".getBytes());
//            while (in.isReadable()) { // (1)
//            System.out.print(in.toString(CharsetUtil.UTF_8));
//                System.out.flush();
//        }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }

//        System.out.println("接收消息:"+msg);
//
//        ((ByteBuf)msg).release();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
