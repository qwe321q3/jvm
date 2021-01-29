package com.tianshuo.framework.protocol.netty;

import com.google.gson.Gson;
import com.tianshuo.framework.Invoke;
import com.tianshuo.framework.protocol.netty.codec.MessageProtocol;
import com.tianshuo.framework.protocol.netty.codec.MyMessageProtocolDecoder;
import com.tianshuo.framework.protocol.netty.codec.MyMessageProtocolEncoder;
import com.tianshuo.framework.util.ProtostuffUtils;
import com.tianshuo.provider.IHello;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName : NettyClient
 * @Description : 用于客户端发送消息
 * @Author : tianshuo
 * @Date: 2021-01-29 14:40
 */
public class NettyClient {

    private int port;

    private String ip;

    public NettyClient() {

    }

    public NettyClient(String ip,int port) {
        this.port = port;
        this.ip = ip;
    }

    public void startup() {

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
                    ch.pipeline().addLast(new MyMessageProtocolEncoder());
                    ch.pipeline().addLast(new MyMessageProtocolDecoder());
                    ch.pipeline().addLast(new NettyClientHandler());
                }
            });


            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 8000).sync();

            Invoke invoke = new Invoke();
            Method method = IHello.class.getMethod("sayHello", String.class);
            invoke.setClassName(IHello.class.getName());
            invoke.setMethodName(method.getName());
            invoke.setParamType(method.getParameterTypes());
            invoke.setParam(new Object[]{"定速度库"});
            Gson gson = new Gson();
            String msg = gson.toJson(invoke);
            System.out.println(msg);
            MessageProtocol messageProtocol = new MessageProtocol(msg.getBytes(StandardCharsets.UTF_8).length,msg.getBytes(StandardCharsets.UTF_8));
            f.channel().writeAndFlush(messageProtocol);
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }



    }

    public static void main(String[] args) {
        new NettyClient("localhost",8000).startup();
    }

}

