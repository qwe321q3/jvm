package com.jvm.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * @ClassName : NettyCodecClient
 * @Description : netty编码解码粘包拆包
 * @Author : tianshuo
 * @Date: 2021-01-22 16:04
 */
public class NettyCodecClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    // 出站需要编码 客户端 --> 服务端  == 出站
//                    ch.pipeline().addLast("decoder", new StringDecoder());
//                    ch.pipeline().addLast("encoder", new StringEncoder());
//                    ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
//                    ch.pipeline().addLast(new ObjectEncoder());
                    // 按照指定符号拆包，解决粘包问题
//                    ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,Unpooled.copiedBuffer("#".getBytes(StandardCharsets.UTF_8))));
                    ch.pipeline().addLast(new MyMessageProtocolEncoder());
                    ch.pipeline().addLast(new NettyCodecClientHandler());
                }
            });


            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 9000).sync();
            Channel channel = f.channel();
            for (int i = 0; i < 200; i++) {
          /*      //ByteBuf传递
                channel.writeAndFlush(Unpooled.copiedBuffer(("消息" + i+"#").getBytes(CharsetUtil.UTF_8)));*/
                //自定义的粘包拆包传递
                String msg = ("消息"+i);
                MessageProtocol messageProtocol = new MessageProtocol();
                messageProtocol.setLen(msg.getBytes(CharsetUtil.UTF_8).length);
                messageProtocol.setBody(msg.getBytes(CharsetUtil.UTF_8));
                System.out.println(messageProtocol);
                channel.writeAndFlush(messageProtocol);
            }
//            Scanner scanner = new Scanner(System.in);
//            while (scanner.hasNext()) {
////                channel.writeAndFlush(Unpooled.copiedBuffer(scanner.next().getBytes(StandardCharsets.UTF_8)));
////                channel.writeAndFlush(new User("1",scanner.next()));
//
//                channel.writeAndFlush(Unpooled.copiedBuffer(ProtostuffUtils.serialize(new User("1",scanner.next()))));
//
//            }

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}

