package com.jvm.netty.bytebuf;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : ByteBufTest
 * @Description : netty的ByteBuf测试实例,readIndex,writeIndex capacity理解
 * @Author : tianshuo
 * @Date: 2021-01-18 16:27
 */
@Slf4j(topic = "ByteBuf")
public class ByteBufTest {

    public static void main(String[] args) {
        // 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
        // 通过readerindex和writerIndex和capacity，将buffer分成三个区域
        // 已经读取的区域：[0,readerindex)
        // 可读取的区域：[readerindex,writerIndex)
        // 可写的区域: [writerIndex,capacity)
        ByteBuf byteBuf = Unpooled.buffer(10);
        log.info("byteBuf=" + byteBuf);

        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        log.info("byteBuf=" + byteBuf);
        // wix = 8  rix = 0 capacity = 8
        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        log.info("byteBuf=" + byteBuf);
        log.info("剩余长度：{}",byteBuf.readableBytes());


        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.readByte());
        }
        log.info("byteBuf=" + byteBuf);

        log.info("剩余长度：{}",byteBuf.readableBytes());
//
//
//        //用Unpooled工具类创建ByteBuf
//        ByteBuf byteBuf2 = Unpooled.copiedBuffer("hello,zhuge!", CharsetUtil.UTF_8);
//        //使用相关的方法
//        if (byteBuf2.hasArray()) {
//            byte[] content = byteBuf2.array();
//            //将 content 转成字符串
//            System.out.println(new String(content, CharsetUtil.UTF_8));
//            System.out.println("byteBuf2=" + byteBuf2);
//
//            System.out.println(byteBuf2.getByte(0)); // 获取数组0这个位置的字符h的ascii码，h=104
//
//            int len = byteBuf2.readableBytes(); //可读的字节数  12
//            System.out.println("len=" + len);
//
//            //使用for取出各个字节
//            for (int i = 0; i < len; i++) {
//                System.out.println((char) byteBuf2.readByte());
//            }
//
//            //范围读取
//            System.out.println(byteBuf2.getCharSequence(0, 6, CharsetUtil.UTF_8));
//            System.out.println(byteBuf2.getCharSequence(6, 6, CharsetUtil.UTF_8));
//            System.out.println(byteBuf2);
//        }

    }
}

