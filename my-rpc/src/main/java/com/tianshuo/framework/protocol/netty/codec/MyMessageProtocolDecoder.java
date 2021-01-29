package com.tianshuo.framework.protocol.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName : MyMessageProtocolEncoder
 * @Description : 自定义编码器，用于解决粘包拆包问题  解码器继承ByteToMessageDecoder
 * @Author : tianshuo
 * @Date: 2021-01-22 16:59
 */
@Slf4j
public class MyMessageProtocolDecoder extends ByteToMessageDecoder {
    int length = 0 ;

    /**
     * 步骤：
     * step1: 先读取4个字节的长度，因为int类型占4个字节
     * step2: 这个报文的长度是否大于可读字节的大小，如果大于说明出现了粘包，这个包还没有完全传递过来，这个直接return；等待下次读取
     * step3: 如果报文长度小于可读字节的大小，说明此时包已经完整的，根据长度读取byte然后构造自定的报文对象，并且将对象放入list中传
     * 给下一个处理器
     * step4: 重置length的长度为0 下次继续从0开始读取
     *
     * todo 解码过程的中length的长度不会从头计算（暂时可以不知道原因，可能是因为这个解码器是被单例调用）
     * @param channelHandlerContext
     * @param byteBuf
     * @param list  传递到下一个业务处理handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println();
        log.error("开始分包解码: {} length为：{}",byteBuf,length);

       log.info("开始解码，{},可读长度为{}",byteBuf,byteBuf.readableBytes());
        if (byteBuf.readableBytes() >= 4) {
            if(length == 0) {
                length = byteBuf.readInt();
            }

            log.info("读取长度之后,length为：{}，可读长度为:{}",length,byteBuf.readableBytes());
            if (length > byteBuf.readableBytes()) {
                log.info("无效包，{} , 等待下次报文",byteBuf);
                return;
            }
            if (length <= byteBuf.readableBytes()) {

                //读取body内容
                byte[] body = new byte[length];
                byteBuf.readBytes(body);
                //拼装Messageprotocol
                MessageProtocol messageProtocol = new MessageProtocol();
                messageProtocol.setLen(length);
                messageProtocol.setBody(body);
                list.add(messageProtocol);
            }

            length  = 0;
        }


    }
}

