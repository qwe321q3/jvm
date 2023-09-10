package com.jvm.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName : NettyCodecServer
 * @Description : netty编码解码粘包拆包
 * 1.netty在不使用解码器时候，默认只能够传递ByteBuf，所以传递writeAndFlush时，需要先把数据转为的ByteBuf来传递
 * @Author : tianshuo
 * @Date: 2021-01-22 16:04
 */
public class NettyCodecServer {

    public static void main(String[] args) throws InterruptedException {
        // 构建线程，绑定的selector
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        System.err.println("准备运行端口：" + 9000);
        try {
            ServerBootstrap b = new ServerBootstrap();
            // 设置的acceptor线程组和用于处理读写的线程组worker
            b.group(bossGroup, workerGroup)
                    // 设置NIO的ServerSocketChannel类型 用与后续反射实例化
                    .channel(NioServerSocketChannel.class)
                    // 设置函数是编程，将自定义的写入写出handler，加入到的pipeline中
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MyMessageProtocolDecoder());
                            ch.pipeline().addLast(new NettyCodecServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口号
            // 初始化pipeline,设置默认的prev和next
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(9000).sync();

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

