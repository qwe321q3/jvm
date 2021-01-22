package com.jvm.netty.codec;

import com.jvm.netty.base.ChatServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName : NettyCodecServer
 * @Description : netty编码解码粘包拆包
 * @Author : tianshuo
 * @Date: 2021-01-22 16:04
 */
public class NettyCodecServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        System.err.println("准备运行端口：" + 9000);
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("decoder", new StringDecoder());
//                            ch.pipeline().addLast("encoder", new StringEncoder());
//                            按照指定符号拆包，解决粘包问题
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("#".getBytes(StandardCharsets.UTF_8))));
                            ch.pipeline().addLast(new NettyCodecServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(9000).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}

