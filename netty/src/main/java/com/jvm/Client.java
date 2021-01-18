package com.jvm;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * 不管服务端是用的BIO,NIO或者是AIO，客户端缺不一定非得一一对应
 * 客户端可以都用BIO
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 8080);

        while(true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            client.getOutputStream().write(input.getBytes());
            byte[]bytes = new byte[1024];
            int len = client.getInputStream().read(bytes);
            System.out.println("msg back "+new String( bytes,0,len));
        }

    }
}
