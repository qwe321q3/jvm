package com.jvm;

import com.jvm.netty.ChatClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {


        int port = 8080;
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
                    //去除编码解码
//                    ch.pipeline().addLast("decoder", new StringDecoder());
//                    ch.pipeline().addLast("encoder", new StringEncoder());
                    ch.pipeline().addLast(new ChatClientHandler());
                }
            });


            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", port).sync();
            Channel channel = f.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                channel.writeAndFlush(Unpooled.copiedBuffer(scanner.next().getBytes(StandardCharsets.UTF_8)));
            }

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
