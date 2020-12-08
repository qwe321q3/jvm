package com.tianshuo.thread.atom;

import com.tianshuo.thread.aqs.AbstractQueuedSynchronizer;
import org.openjdk.jol.info.ClassLayout;
import sun.misc.Unsafe;

/**
 * @Classname AddressPointorTest
 * @Description
 * @Date 2020/8/21 0021 22:20
 * @Created by Administrator
 */
public class AddressPointorTest {


    /*ageOffset: 12
    ccOffset: 16
    com.tianshuo.thread.atom.AddressPointorTest object internals:
    OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
            4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
            8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
            12     4    int AddressPointorTest.age                    7
            16     4    int AddressPointorTest.cc                     5
            20     4        (loss due to the next object alignment)
    Instance size: 24 bytes
    Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

    com.tianshuo.thread.atom.AddressPointorTest object internals:
    OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
            4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
            8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
            12     4    int AddressPointorTest.age                    7
            16     4    int AddressPointorTest.cc                     8
            20     4        (loss due to the next object alignment)
    Instance size: 24 bytes
    Space losses: 0 bytes internal + 4 bytes external = 4 bytes total*/

    private int age = 7;

    private int cc = 5;

    //记录属性age属性在内存中的偏移量（也可以叫做内存中的地址），Cas操作会根据这个偏移来修改属性的值
    private static final long ageOffset ;
    private static final long ccOffset ;



    static{

        try {
            ageOffset = UnSafeInstance.createUnsafe().objectFieldOffset
                    (AddressPointorTest.class.getDeclaredField("age"));
            ccOffset = UnSafeInstance.createUnsafe().objectFieldOffset
                    (AddressPointorTest.class.getDeclaredField("cc"));

            System.out.println("ageOffset: "+ageOffset);
            System.out.println("ccOffset: "+ccOffset);
        } catch (NoSuchFieldException ex) {
            throw new Error(ex);
        }

    }


    public boolean compareAndSweep(int expect, int newValue) {
        return UnSafeInstance.createUnsafe().compareAndSwapInt(this, ageOffset, expect, newValue);
    }

    public boolean compareAndSweepCC(int expect, int newValue) {
        return UnSafeInstance.createUnsafe().compareAndSwapInt(this, ccOffset, expect, newValue);
    }

    public static void main(String[] args) {
        AddressPointorTest addressPointorTest = new AddressPointorTest();


        System.out.println(ClassLayout.parseInstance(addressPointorTest).toPrintable());



        addressPointorTest.compareAndSweepCC(5,8);


        System.out.println(ClassLayout.parseInstance(addressPointorTest).toPrintable());



    }

}
