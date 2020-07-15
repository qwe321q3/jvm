package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 数据对象
 * 用来计算对象大小
 * <p>
 * 开启指针压缩
 * markword 8 + klassponit 4 +  实例数据(4+4)+(数组4)+ 数据填充 0 = 24;
 * <p>
 * 未开启指针压缩 -XX:-UseCompressedOops
 * markword 8 + Klass Point 8 + 实例数据(4+4)+ (数组4*3) + 数据填充(4) = 32;
 */
public class ArrayObject {

    int a = 11;
    int b = 10;

    int[] array = {4, 2, 5};

    public static void main(String[] args) {
        /**
         * 开启指针压缩
         * com.jvm.objectstruct.ArrayObject object internals:
         *  OFFSET  SIZE    TYPE DESCRIPTION                               VALUE
         *       0     4         (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4         (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4         (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4     int ArrayObject.a                             0
         *      16     4     int ArrayObject.b                             10
         *      20     4   int[] ArrayObject.array                         [4, 2, 5]
         * Instance size: 24 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */


        /**
         *  未开启指针压缩
         *  com.jvm.objectstruct.ArrayObject object internals:
         *  OFFSET  SIZE    TYPE DESCRIPTION                               VALUE
         *       0     4         (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4         (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4         (object header)                           28 30 ad 1b (00101000 00110000 10101101 00011011) (464334888)
         *      12     4         (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *      16     4     int ArrayObject.a                             0
         *      20     4     int ArrayObject.b                             10
         *      24     8   int[] ArrayObject.array                         [4, 2, 5]
         * Instance size: 32 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */
        ArrayObject object = new ArrayObject();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);
    }
}
