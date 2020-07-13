package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 空对象
 * 计算对象大小
 *
 *  -- 开启压缩 -XX:+UseCompressedOops
 *  markword (8) + klasspoint (4) + 实例数据(0) + 填充(4) = 16
 *  -- 关闭指针压缩 -XX:-UseCompressedOops
 *  markword (8) + klasspoint (8) + 实例数据(0) + 填充(0) = 16
 */
public class EmptyObject {

    public static void main(String[] args) {

       /*
        开启指针压缩
        com.jvm.objectstruct.EmptyObject object internals:
        OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
        4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
        8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
        12     4        (loss due to the next object alignment)
        Instance size: 16 bytes
        */

        /**
         * 未开启指针压缩
         * com.jvm.objectstruct.EmptyObject object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           28 30 ef 1b (00101000 00110000 11101111 00011011) (468660264)
         *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         * Instance size: 16 bytes
         */

        EmptyObject object = new EmptyObject();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);

    }
}
