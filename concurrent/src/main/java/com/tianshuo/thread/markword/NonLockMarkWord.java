package com.tianshuo.thread.markword;

import org.openjdk.jol.info.ClassLayout;

/**
 * @ClassName : NonLockMarkWord  //类名
 * @Description : 展示无锁状态下的对象头markword内容，java的markword采用的小端序，所以要从后往前读
 * @Author : tianshuo  //作者
 * @Date: 2020-07-18 14:12  //时间
 */
public class NonLockMarkWord {
    static Object a ;

    public static void main(String[] args) {

        NonLockMarkWord nonLockMarkWord = new NonLockMarkWord();
        String layout = ClassLayout.parseInstance(nonLockMarkWord).toPrintable();

        /**
         *
         * com.tianshuo.thread.markword.NonLockMarkWord object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4        (loss due to the next object alignment)
         * Instance size: 16 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        /**
         * 小端序
         * 00000001 00000000 00000000 00000000 00000000 00000000 00000000 00000000
         * 读取方式 后3位第一位的0标识可以未偏向，01 标识
         * 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000 0 01
         */
        System.out.println(layout);


        a = new Object();
        System.out.println(ClassLayout.parseInstance(a).toPrintable());

        synchronized (a){
            System.out.println(ClassLayout.parseInstance(a).toPrintable());
        }


    }
}

