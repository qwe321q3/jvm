package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 多路复用器
 */
public class QQServerNioListener {

    public static void main(String[] args) throws IOException, InterruptedException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 等有事件发生了才会继续
            // select 为阻塞方法
            selector.select(2000);
            System.out.println("wait handle..");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
//            for (SelectionKey selectionKey :selectionKeys){

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
                    ByteBuffer buffer = ByteBuffer.allocate(80);
                    if(socketChannel.read(buffer) >0){
//                        socketChannel.read(buffer) ;
                        buffer.flip();
                        System.out.println(new String(buffer.array()));
                        ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
                        socketChannel.write(byteBuffer);

                    } else if (socketChannel.read(buffer) == 0) {
                        System.out.println("未读取到数据");
                        // 等于0没有读取到东西，不做处理
                        break;
                    } else {
                        System.err.println("客户端断开连接");
                        // 当读取到-1 ，说明客户端断开了连接，关闭客户端
                        socketChannel.close();
                        break;
                    }

                }
                iterator.remove();
//                if(selectionKey.isWritable()){
//                    System.out.println("write data");
//                    ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
//                    SocketChannel socketChannel  = (SocketChannel) selectionKey.channel();
//                    socketChannel.write(byteBuffer);
//                }
//                selectionKeys.remove(selectionKey);
//                selectionKeys.clear();
            }

        }

   }
}
