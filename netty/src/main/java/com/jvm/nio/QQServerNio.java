package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 未使用的io多用复用的NIO
 *
 * nio 轮询方式实现的NIO，有空轮询效率问题
 * 1、nio与bio相比第一个区别就是可以设置为的非阻塞
 * 2、nio多路复用  reactor模式
 * 比如100W个连接进来，但是只有20W连接会有写消息，但是此方式还是会循环100W次
 *
 * 最好的办法还是让系统能够在有消息的时候通知我们的读取消息  linux epoll
 */
public class QQServerNio {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);

        List<SocketChannel> socketChannelList = new ArrayList<SocketChannel> ();
        while(true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel==null){
                System.out.println("no socket connection ......");
            }else {
                System.out.println("connection  success .....");
                socketChannel.configureBlocking(false);
                socketChannelList.add(socketChannel);
            }

            for (SocketChannel clientSocket : socketChannelList){
                ByteBuffer byteBuffer = ByteBuffer.allocate(20);
                if(clientSocket.read(byteBuffer)!=0) {
                    clientSocket.read(byteBuffer);
                    byteBuffer.flip();
                    System.out.println(new java.lang.String(byteBuffer.array()));
                    ByteBuffer writeByteBuffer = ByteBuffer.wrap("success".getBytes());
                    clientSocket.write(writeByteBuffer);
                }
            }
            Thread.sleep(1500);
        }
   }
}
