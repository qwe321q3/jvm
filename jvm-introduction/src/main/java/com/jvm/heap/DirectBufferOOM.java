package com.jvm.heap;

import java.nio.ByteBuffer;

/**
 * 直接内存
 *
 * 直接内存使用不当也会造成内存溢出
 *　directBuffer
 *
 * -XX:+PrintGCDetails
 * -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/home/tianshuo/tc.hprof
 * -XX:OnOutOfMemoryError=/home/tianshuo/Dasktop/a.sh
 * -Xmx:
 */
public class DirectBufferOOM {

    public static void main(String[] args) {
        for (int i = 0; i < 1024 ; i++) {
            ByteBuffer.allocateDirect(1024 * 1024*1024);
            System.out.println(i);
//            System.gc();  没用上,jdk8会回收直接内存.
        }

    }

}
