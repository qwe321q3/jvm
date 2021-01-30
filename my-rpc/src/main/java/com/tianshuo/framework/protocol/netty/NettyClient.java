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
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;

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

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

//    private static ExecutorService executorService = Executors.newCachedThreadPool();


    NettyClientHandler nettyClientHandler;

    public void startup() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        nettyClientHandler = new NettyClientHandler();
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
                    ch.pipeline().addLast(nettyClientHandler);
                }
            });


            // Start the client.
             b.connect("127.0.0.1", 8000).sync();

            /*Invoke invoke = new Invoke();
            Method method = IHello.class.getMethod("sayHello", String.class);
            invoke.setClassName(IHello.class.getName());
            invoke.setMethodName(method.getName());
            invoke.setParamType(method.getParameterTypes());
            invoke.setParam(new Object[]{"定速度库"});
            try(
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ObjectOutputStream sOut = new ObjectOutputStream(out);
            ){
                sOut.writeObject(invoke);
                sOut.flush();
                byte[] bytes = out.toByteArray();
                MessageProtocol messageProtocol = new MessageProtocol(bytes.length,bytes);
                f.channel().writeAndFlush(messageProtocol);
            }*/
            // Wait until the connection is closed.
//            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }/* finally {
            workerGroup.shutdownGracefully();
        }*/

    }

//    消息发送
    public Object send(Invoke invoke) throws ExecutionException, InterruptedException {

        if (nettyClientHandler == null) {
            startup();
        }
        //单线程发送
//        return nettyClientHandler.call(invoke);

        nettyClientHandler.setInvoke(invoke);
        Future submit = threadPoolExecutor.submit(nettyClientHandler);
        return submit.get();
//        return executorService.submit(nettyClientHandler).get();
    }



    public static void main(String[] args) {
        new NettyClient("localhost",8000).startup();
    }

}

