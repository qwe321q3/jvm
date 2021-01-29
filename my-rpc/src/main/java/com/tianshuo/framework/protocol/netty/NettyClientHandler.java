package com.tianshuo.framework.protocol.netty;

import com.google.gson.Gson;
import com.tianshuo.framework.protocol.netty.codec.MessageProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : NettyClient
 * @Description : 用于客户端发送消息
 * @Author : tianshuo
 * @Date: 2021-01-29 14:40
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 连接服务端方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("已经连接");
        super.channelActive(ctx);
    }

    /**
     * 消息读取
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("netty客户端：{},读取数据",channel.remoteAddress());
        MessageProtocol messageProtocol = (MessageProtocol) msg;
        log.info("服务端数据为：",new String(messageProtocol.getBody(), CharsetUtil.UTF_8));
        super.channelRead(ctx, msg);
    }
}

