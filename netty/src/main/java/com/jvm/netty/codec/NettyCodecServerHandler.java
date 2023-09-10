package com.jvm.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName : NettyCodecServerHandler
 * @Description : 服务处理器
 * @Author : tianshuo
 * @Date: 2021-01-22 16:07
 */
public class NettyCodecServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(NettyCodecServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageProtocol messageProtocol = (MessageProtocol) msg;


//        MessageProtocol messageProtocol = new MessageProtocol();
//        messageProtocol.setLen(msg.getBytes(CharsetUtil.UTF_8).length);
//        messageProtocol.setBody(msg.getBytes(CharsetUtil.UTF_8));
//        channel.writeAndFlush(messageProtocol);

        logger.info("消息 ：长度为：{},消息内容为： {}",messageProtocol.getLen(),new String(messageProtocol.getBody(),CharsetUtil.UTF_8));
        super.channelRead(ctx, msg);
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("{},已经上线",ctx.channel().remoteAddress());

        super.channelActive(ctx);
    }
}

