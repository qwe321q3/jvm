package com.jvm.lock;

/**
 * 锁消除
 * -XX:+EliminateLocks
 * 未加锁消除时时间：2257 ms
 * 加了锁消除时间：1823 ms
 *
 * 偏向锁：1825
 * -XX:+UseBiasedLocking
 * -XX:BiasedLockingStartupDelay=
 *
 */
public class LockEliminate {
    private static final int CIRCLE =20000000;
    public static void main(String[] args) {

        long startTime  = System.currentTimeMillis();
        for (int i = 0; i < CIRCLE ; i++) {

          createString("jvm","Test");
        }
        long endTime = System.currentTimeMillis();

        System.out.println(endTime-startTime);

    }

    private static String createString(String jvm, String test) {
        StringBuffer sb = new StringBuffer();
        sb.append(jvm);
        sb.append(test);
        return sb.toString();
    }
}
