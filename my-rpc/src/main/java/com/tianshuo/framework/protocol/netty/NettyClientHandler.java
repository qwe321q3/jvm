package com.tianshuo.framework.protocol.netty;

import com.google.gson.Gson;
import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.netty.codec.MessageProtocol;
import com.tianshuo.framework.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

/**
 * @ClassName : NettyClient
 * @Description : 用于客户端发送消息
 * @Author : tianshuo
 * @Date: 2021-01-29 14:40
 */
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext cx ;
    private Object result;
    private Invoke invoke;

    /**
     * 连接服务端方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        cx = ctx;
        log.info("已经连接");
//        super.channelActive(ctx);
    }

    /**
     * 消息读取
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        log.info("netty客户端：{},读取数据",channel.remoteAddress());
        MessageProtocol messageProtocol = (MessageProtocol) msg;

        result = new String(messageProtocol.getBody(), CharsetUtil.UTF_8);
        log.info("{}",messageProtocol);
        log.info("服务端数据为：{}",result);
        notify();
    }

    public void setInvoke(Invoke invoke)  {
        this.invoke = invoke;
    }

    /**
     * 单线程发送方法
     * @param invoke
     * @return
     */
    public synchronized Object call(Invoke invoke){
        log.info("netty client: 调用call {}",invoke);

        MessageProtocol messageProtocol = new MessageProtocol();
        byte[] serialize = ProtostuffUtils.serialize(invoke);
        messageProtocol.setLen(serialize.length);
        messageProtocol.setBody(serialize);
        log.info("客户端：messageProtocol,{}",messageProtocol);

        cx.writeAndFlush(messageProtocol);
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }



    @Override
    public synchronized Object call() throws Exception {
        log.info("netty MultiThread client: 调用call {}",invoke);

        MessageProtocol messageProtocol = new MessageProtocol();
        byte[] serialize = ProtostuffUtils.serialize(invoke);
        messageProtocol.setLen(serialize.length);
        messageProtocol.setBody(serialize);
        log.info("客户端：messageProtocol,{}",messageProtocol);

        cx.writeAndFlush(messageProtocol);
        wait();
        return result;
    }
}

