package com.jvm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;

/**
 * @ClassName : NioReactorServer
 * @Description : 1多路复用器负责连接，2个多路复用器负责数据读取
 * @Author : tianshuo
 * @Date: 2021-02-02 17:39
 */
public class NioReactorServer {


    private Selector acceptSelector;

    private Selector r1Selector;

    private Selector r2Selector;

//    private Queue

    public void startup() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8000));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(acceptSelector, SelectionKey.OP_ACCEPT);
        while (true) {
            if (acceptSelector.select() > 0) {
                Set<SelectionKey> selectionKeys = acceptSelector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {


                }


            } else {
                continue;
            }
        }
    }


    public void handleAccept(SelectionKey selectionKey) {

    }

    public void handleRead(SelectionKey selectionKey) {

    }


}

