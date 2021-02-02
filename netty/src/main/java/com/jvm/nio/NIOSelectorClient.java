package com.jvm.nio;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Nio客户端+多路复用器
 * @author tianshuo
 */
public class NIOSelectorClient {

    public static Selector selector;
    public static SocketChannel socketChannel;

    static {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8000));
            // 绑定socketChannel与多路复用器Selector的关系，同时关注READ事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            while (!socketChannel.finishConnect()){

            }
            System.out.println("已连接！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
            SocketChannel socketChannel = NIOSelectorClient.socketChannel;
//            ByteBuffer byteBuffer = ByteBuffer.allocate(1);

            new Avca(selector,socketChannel).start();
        ByteBuffer byteBuffer = null;
            while (true){
                Scanner scanner = new Scanner(System.in);
                String word = scanner.nextLine();
              /*  ByteBuffer byteBuffer = ByteBuffer.allocate(word.getBytes().length);
                byteBuffer.put(word.getBytes(StandardCharsets.UTF_8));
                byteBuffer.flip();*/
                byteBuffer = null;
                byteBuffer = ByteBuffer.wrap(word.getBytes(StandardCharsets.UTF_8));
//                byteBuffer.put(word.getBytes());
                socketChannel.write(byteBuffer);
                byteBuffer.clear();
            }
    }

    static class Avca extends Thread{
        private Selector selector;
        private SocketChannel clntChan;

       public Avca(Selector selector,SocketChannel clntChan){
            this.selector = selector;
            this.clntChan = clntChan;
       }

        @Override
        public void run(){
            try {
                while (true){
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                    while (keyIterator.hasNext()){
                        SelectionKey selectionKey = keyIterator.next();
                        if (selectionKey.isValid()){
                            if (selectionKey.isReadable()){

                                SocketChannel socketChannel = (SocketChannel)selectionKey.channel();

                                if(socketChannel.read(byteBuffer)>0) {

                                    byteBuffer.flip();
                                    byte[] bytes = new byte[byteBuffer.remaining()];
                                    byteBuffer.get(bytes);
                                    System.out.println(new String(bytes));
                                    byteBuffer.clear();
                                } else if (socketChannel.read(byteBuffer) == 0) {
                                    System.out.println("未写数据");
                                    continue;
                                } else {
                                    System.err.println("连接断开");
                                    socketChannel.close();
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}