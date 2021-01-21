package com.jvm.netty.base;

import com.jvm.model.User;
import com.jvm.util.ProtostuffUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {


        int port = 8000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
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
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });


            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", port).sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
//                channel.writeAndFlush(Unpooled.copiedBuffer(scanner.next().getBytes(StandardCharsets.UTF_8)));
//                channel.writeAndFlush(new User("1",scanner.next()));

                channel.writeAndFlush(Unpooled.copiedBuffer(ProtostuffUtils.serialize(new User("1",scanner.next()))));

            }

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
