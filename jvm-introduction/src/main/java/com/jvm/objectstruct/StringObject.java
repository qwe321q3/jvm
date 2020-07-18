package com.jvm.objectstruct;

import org.openjdk.jol.info.ClassLayout;

/**
 * 简单对象
 * 计算对象大小
 * <p>
 * -- 开启压缩 -XX:+UseCompressedOops
 * markword (8) + klasspoint (4) + 实例数据(4+4+4) + 填充(0) = 24
 * -- 关闭指针压缩 -XX:-UseCompressedOops
 * markword (8) + klasspoint (8) + 实例数据(4+4+4) + 填充(4) = 32
 */
public class StringObject {
    int a,b = 0;
    // String类型占用4个字节
    String c = "我是谁";
    public static void main(String[] args) {

        /**
         * 开启指针压缩
         * com.jvm.objectstruct.StringObject object internals:
         *  OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
         *       0     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4                    (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4                int StringObject.a                            0
         *      16     4                int StringObject.b                            0
         *      20     4   java.lang.String StringObject.c                            (object)
         * Instance size: 24 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */

        /**
         * 关闭指针压缩 -XX:-UseCompressedOops
         * com.jvm.objectstruct.StringObject object internals:
         *  OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
         *       0     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *       4     4                    (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
         *       8     4                    (object header)                           28 00 b8 15 (00101000 00000000 10111000 00010101) (364380200)
         *      12     4                    (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
         *      16     4                int StringObject.a                            0
         *      20     4                int StringObject.b                            0
         *      24     8   java.lang.String StringObject.c                            (object)
         * Instance size: 32 bytes
         * Space losses: 0 bytes internal + 0 bytes external = 0 bytes total
         */

        StringObject object = new StringObject();
        String layout = ClassLayout.parseInstance(object).toPrintable();

        System.out.println(layout);

    }
}
