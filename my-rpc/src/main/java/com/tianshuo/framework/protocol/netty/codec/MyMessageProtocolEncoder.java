package com.tianshuo.framework.protocol.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : MyMessageProtocolEncoder
 * @Description : 自定义编码器，用于解决粘包拆包问题  编码器继承MessageToByteEnCoder
 * @Author : tianshuo
 * @Date: 2021-01-22 16:59
 */
@Slf4j
public class MyMessageProtocolEncoder extends MessageToByteEncoder<MessageProtocol> {
    /**
     *
     * @param channelHandlerContext  管道处理上下文
     * @param messageProtocol 泛型自定义的编码类
     * @param byteBuf byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol, ByteBuf byteBuf) throws Exception {
        log.error("开始编码，{}",messageProtocol);
        byteBuf.writeInt(messageProtocol.getLen());
        byteBuf.writeBytes(messageProtocol.getBody());

    }
}

