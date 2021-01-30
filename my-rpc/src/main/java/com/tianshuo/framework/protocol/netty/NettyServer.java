package com.tianshuo.framework.protocol.netty;

import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.Protocol;
import com.tianshuo.framework.protocol.netty.codec.MyMessageProtocolDecoder;
import com.tianshuo.framework.protocol.netty.codec.MyMessageProtocolEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutionException;

/**
 * netty服务
 * @author tianshuo
 */
public class NettyServer implements Protocol {

    private int port;

    private String ip;

    public NettyServer() {
    }

    public NettyServer(String ip,int port) {
        this.port = port;
        this.ip = ip;
    }

    @Override
    public void startup() {
        // (1)
        EventLoopGroup bossGroup = new NioEventLoopGroup(5);
        EventLoopGroup workerGroup = new NioEventLoopGroup(5);
        System.err.println("准备运行端口：" + port);

        try {
            // (2)
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // (3)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 入站
                            ch.pipeline().addLast(new MyMessageProtocolEncoder());
                            ch.pipeline().addLast(new MyMessageProtocolDecoder());
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    })
                    // (5)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // (6)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            // (7)
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public Object send(Invoke invoke) {

        NettyClient nettyClient = new NettyClient("localhost", 8000);
        nettyClient.startup();
        try {
            return nettyClient.send(invoke);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
