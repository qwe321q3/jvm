package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Nio Reactor模式
 *  1个selector/boss + workersPool
 *
 *
 */
public class QQServerNioPool {

    public static void main(String[] args) throws IOException, InterruptedException {
        //工人线程池
        ExecutorService workerPool = Executors.newFixedThreadPool(5);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 此方法为阻塞方法
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
                    workerPool.execute(() -> {
                        try {
                            System.out.println(Thread.currentThread().getName()+" -- handle data");
                            SocketChannel socketChannel  = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(40);

                            if(socketChannel.read(buffer) >0){
                                socketChannel.read(buffer) ;
                                buffer.flip();
                                System.out.println(new String(buffer.array()));
                                System.out.println(Thread.currentThread().getName()+" -- " +new String(buffer.array()));

                                ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
                                socketChannel.write(byteBuffer);

                            } else if (socketChannel.read(buffer) == 0) {
                                System.out.println("未读取到数据");
                                // 等于0没有读取到东西，不做处理
                            } else {
                                // 当读取到-1 ，说明客户端断开了连接，关闭客户端
                                socketChannel.close();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
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
