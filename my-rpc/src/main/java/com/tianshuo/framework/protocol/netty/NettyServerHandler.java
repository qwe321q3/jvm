package com.tianshuo.framework.protocol.netty;

import com.google.gson.Gson;
import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.netty.codec.MessageProtocol;
import com.tianshuo.framework.registry.LocalRegistry;
import com.tianshuo.framework.util.ProtostuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * netty处理服务
 * @author tianshuo
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 数据读取
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("读取客户端,{}数据",ctx.channel().remoteAddress());

        MessageProtocol messageProtocol = (MessageProtocol) msg;
        System.out.println(messageProtocol.getLen());

        Gson gson = new Gson();
        Invoke invoke = gson.fromJson(new String(messageProtocol.getBody(), CharsetUtil.UTF_8), Invoke.class);
        Class clazz = LocalRegistry.get(invoke.getClassName());

        Method method = clazz.getMethod(invoke.getMethodName(),String.class);

        Object invoke1 = method.invoke(clazz.newInstance(), invoke.getParam());


        String res = gson.toJson(invoke1);
        MessageProtocol messageProtocol1 = new MessageProtocol(res.getBytes(StandardCharsets.UTF_8).length,res.getBytes(StandardCharsets.UTF_8));

        ctx.channel().writeAndFlush(messageProtocol1);

        super.channelRead(ctx, msg);

    }

    /**
     * 已连接服务端
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("netty客户端已连接,{}",ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
}
