package com.jvm.heap;

/**
 * oom及dump文件，另外配置oom时执行的脚本
 * -XX:OnOutOfMemoryError=shell脚本路径   应用出现OOM时，自动执行此脚本。 如：d:/s.sh
 * -XX:HeapDumpPath=dump文件路径  如：d:/tc.dump
 * -XX:HeapOnDumpOutOfMemoryError 程序发生OOM时，记录整个堆的信息。
 */
public class DumpOOM {

    public static void main(String[] args) {
        System.out.println(44);
        byte[] bytes = new byte[2 * 1024 * 1024];
    }
}
