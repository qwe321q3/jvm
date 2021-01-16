package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * nio 轮询方式实现的NIO，有空轮询效率问题
 * 比如100W个连接进来，但是只有20W连接会有写消息，但是此方式还是会循环100W次
 *
 * 最好的办法还是让系统能够在有消息的时候通知我们的读取消息  linux epoll
 */
public class QQServerNioListener {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // select 为阻塞方法
            selector.select(2000);//等有事件发生了才会继续
            System.out.println("wait handle..");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            for (SelectionKey selectionKey :selectionKeys){

                //连接如果是无效的就跳过，channel已经关闭了
                if(!selectionKey.isValid()){
                    continue;
                }
                //如果客户端是要连接
                if(selectionKey.isAcceptable()){
                    System.err.println("accept connection...");
                    ServerSocketChannel ServerSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = ServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }
                //如果是有客户端要读取
                if(selectionKey.isReadable()){
                    System.out.println("read data");

                    SocketChannel socketChannel  = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(40);
                    if(socketChannel.read(buffer)!=0){
                        socketChannel.read(buffer) ;
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
                        socketChannel.write(byteBuffer);

                    }

                }

//                if(selectionKey.isWritable()){
//                    System.out.println("write data");
//                    ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
//                    SocketChannel socketChannel  = (SocketChannel) selectionKey.channel();
//                    socketChannel.write(byteBuffer);
//                }
                selectionKeys.remove(selectionKey);
                selectionKeys.clear();
            }

        }

   }
}
