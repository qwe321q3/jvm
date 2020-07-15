package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 简单对象
 * 计算对象大小
 * <p>
 * -- 开启压缩 -XX:+UseCompressedOops
 * markword (8) + klasspoint (4) + 实例数据(4+4) + 填充(4) = 24
 * -- 关闭指针压缩 -XX:-UseCompressedOops
 * markword (8) + klasspoint (8) + 实例数据(4+4) + 填充(0) = 24
 */
public class SimpleObject {
    int a,b = 0;
    public static void main(String[] args) {

        /**
         * 开启指针压缩，填充了4位
         * com.jvm.objectstruct.SimpleObject object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4    int SimpleObject.a                            0
         *      16     4    int SimpleObject.b                            0
         *      20     4        (loss due to the next object alignment)
         * Instance size: 24 bytes
         * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        /**
         * 未开启数据填充 -XX:-UseCompressedOops
         * com.jvm.objectstruct.SimpleObject object internals:
         *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
         *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4        (object header)                           28 30 bd 1b (00101000 00110000 10111101 00011011) (465383464)
         *      12     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *      16     4    int SimpleObject.a                            0
         *      20     4    int SimpleObject.b                            0
         * Instance size: 24 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         *
         */

        SimpleObject object = new SimpleObject();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);

    }
}
