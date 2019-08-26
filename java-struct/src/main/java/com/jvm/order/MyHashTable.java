package com.jvm.order;

import com.jvm.linetable.HashTable;

/**
 * 模拟哈希表
 *
 * 采用方式： 顺序表  + 链表;
 * 装填因子：为0.5
 * hash算法：求余数
 */
public class MyHashTable {
    public static void main(String[] args) {


//         Integer index = new Integer(75);
//         System.out.println(index.hashCode(index));

//        int[] arrs = new int[]{23,65,3,63,89,33,12,90,45};


        HashTable hashTable = new HashTable();

        hashTable.add(23);
        hashTable.add(65);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(16);
        hashTable.add(55);
        hashTable.add(11);


        hashTable.get();


    }

}

