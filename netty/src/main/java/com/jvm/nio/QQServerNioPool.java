package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Nio Reactor模式
 * 1个selector/boss + workersPool
 */
public class QQServerNioPool {

    public static void main(String[] args) throws IOException, InterruptedException {
        //工人线程池
        ExecutorService workerPool = Executors.newFixedThreadPool(5);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        // 绑定ServerSocketChannel与 selector的关系到 SelectedKey（ServerSocketChannel和SocketChannel有共同父类）
        // 所以才可以通过SelectKey获取到channel 和Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            // 此方法为阻塞方法//等有事件发生了才会继续
           selector.select(2000);
            System.out.println("wait handle..");
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                //连接如果是无效的就跳过，channel已经关闭了
                if (!selectionKey.isValid()) {
                    continue;
                }
                //如果客户端是要连接
                if (selectionKey.isAcceptable()) {
                    System.err.println("accept connection...");
                    ServerSocketChannel ServerSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = ServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // 可以在连接之后，直接税配置一个ByteBuffer
                    // socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(44));

                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                //如果是有客户端要读取
                if (selectionKey.isReadable()) {
                    System.out.println("read data");
                    workerPool.execute(() -> {
                        try {
                            System.out.println(Thread.currentThread().getName() + " -- handle data");
                            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                            // 获取连接中配置的ByteBuffer
                            // ByteBuffer attachment = (ByteBuffer) selectionKey.attachment();
                            ByteBuffer buffer = ByteBuffer.allocate(80);
                            if (socketChannel.read(buffer) > 0) {

                                socketChannel.read(buffer);
                                buffer.flip();
                                System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));
                                System.out.println(Thread.currentThread().getName() + " -- " + new String(buffer.array()));

                                ByteBuffer byteBuffer = ByteBuffer.wrap("success".getBytes());
                                socketChannel.write(byteBuffer);

                            } else if (socketChannel.read(buffer) == 0) {
                                System.out.println("未读取到数据");
                                // 等于0没有读取到东西，不做处理
                            } else {
                                System.err.println("客户端断开连接");
                                // 当读取到-1 ，说明客户端断开了连接，关闭客户端
                                socketChannel.close();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                iterator.remove();
            }

        }

    }
}
