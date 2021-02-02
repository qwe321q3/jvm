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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName : NioReactorServer
 * @Description : 1多路复用器负责连接，2个多路复用器负责数据读取
 * acceptSelector负责客户端的连接
 * r0Selector和r1Selector负责的数据的读取和写入
 * 此有点类似Netty
 * @Author : tianshuo
 * @Date: 2021-02-02 17:39
 */
public class NioReactorServer {


    private Selector acceptSelector;

    private Selector r0Selector;

    private Selector r1Selector;

    AtomicInteger atl = new AtomicInteger();


//    private ArrayList<Selector> selectRouteQueue = new ArrayList<>(2);

    public void startup() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        acceptSelector = Selector.open();
        r0Selector = Selector.open();
        r1Selector = Selector.open();
        serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
        new Thread(new NioEventLoop("r0NioEventLoop", 0, r0Selector)).start();
        new Thread(new NioEventLoop("r1NioEventLoop", 1, r1Selector)).start();

        while (true) {
            System.err.println("等待连接");
            acceptSelector.select(2000);
            Set<SelectionKey> selectionKeys = acceptSelector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                handleAccept(selectionKey);
                iterator.remove();
            }

        }
    }


    public void handleAccept(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isValid()) {
            if (selectionKey.isAcceptable()) {
                System.out.println("建立连接");
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel channel = serverSocketChannel.accept();
                channel.configureBlocking(false);
                if (atl.getAndIncrement() % 2 == 0) {
                    System.out.println(channel.getRemoteAddress() + " -- 注册到r0Selector");
                    channel.register(r0Selector, SelectionKey.OP_READ);
                } else {
                    System.out.println(channel.getRemoteAddress() + " -- 注册到r1Selector");
                    channel.register(r1Selector, SelectionKey.OP_READ);
                }
            }
        }

    }


    class NioEventLoop implements Runnable {

        private String name;

        private int routeId;

        private Selector selector;


        public NioEventLoop() {
        }

        public NioEventLoop(String name, int routeId, Selector selector) {
            this.name = name;
            this.routeId = routeId;
            this.selector = selector;
        }

        @Override
        public void run() {
            System.out.println(name + " ---  路由  --- " + routeId);

            Selector routeSelector = selector;

            try {
                while (true) {
//                    if (routeSelector.select(1) > 0) {
                    routeSelector.select(200);
                    Set<SelectionKey> selectionKeys = routeSelector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        handleRead(selectionKey);
                        iterator.remove();
                    }
                }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        public void handleRead(SelectionKey selectionKey) throws IOException {
            if (selectionKey.isReadable()) {
                System.out.println("读取数据 data");

                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer buffer = ByteBuffer.allocate(80);
                if (socketChannel.read(buffer) > 0) {
                    buffer.flip();
                    System.out.println(new String(buffer.array()));
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

            }
        }


    }

    public static void main(String[] args) throws IOException {
        NioReactorServer nioReactorServer = new NioReactorServer();
        nioReactorServer.startup();
    }

}


