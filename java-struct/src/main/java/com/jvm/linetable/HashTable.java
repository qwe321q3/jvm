package com.jvm.linetable;

import java.util.Arrays;

/**
 * 模拟哈希表
 *
 * 采用方式： 顺序表  + 链表;
 * 装填因子：为0.5
 * hash算法：去留求余算法
 *
 * 1、一次保存
 * 2、多次保存 / 冲突保存
 * 3、值一样是不保存
 */
public class HashTable {

    /**
     * 装填因子 0.5左右为最佳   =  记录数/数据长度
     */
    private final float factor = 0.5f;

    /**
     * 设置的顺序表默认容积 默认16
     * 0 - 15
     *
     *
     */
    private final int DEFAULT_CAPACITY = 16;

    /**
     * 顺序表 中每个元素都是一个链表
     */
    private Node[] elementData ;

    /**
     * 空值的数组
     */
    private final Node[] DEFAULT_EMPTY_CAPACITY = new Node[0];


    public HashTable(Node[] elementData) {
        this.elementData = elementData;
    }

    public HashTable() {
        this(0);
    }

    public HashTable(int capacity) {
        if (capacity == 0) {
            this.elementData = new Node[DEFAULT_CAPACITY];
        }else{
            this.elementData = new Node[capacity];
        }
    }

    /**
     * 自定义hash算法  -- 除留取余法
     * @param object
     * @return
     */
    private int hash(Object object){

        if(object instanceof Integer){
            return ((Integer)object.hashCode())%DEFAULT_CAPACITY;
        }else if(object instanceof String){
            return (String.valueOf(object).hashCode())%DEFAULT_CAPACITY;
        }
        return 0;
    }

    /**
     * hash表中保存值
     * @param object
     */
    public void add(Object object){

        //获取对象的hash值
        int key  =  this.hash(object);
        System.out.println("hash: "+key+ "\tvalue: "+object);
        //判断根据hash判断在hash表中寻找查看数据是否在hash表，是否是第一次保存
        if(this.elementData[key]==null){
            Node head = new Node();
            //第一次保存，直接保存
            elementData[key] = head;
            //创建链表节点
            Node node = new Node(object);

            head.next = node;

        }else{

            Node head = this.elementData[key];
            Node node  = head.next;
            //相同时，不保存数据
            while(node.next!=null){
                if(node.data.equals(object)){
                   return;
                }
                node = node.next;
            }

                //已经存在，并且不相等，保存在链表当中
                //保存在链表中
              node = elementData[key];
                while(true){
                    if(node.next == null){
                        break;
                    }
                    node = node.next;
                }
                Node currentNode = new Node(object);
                node.next = currentNode;

        }
        System.out.println("有序表记录个数："+elementData.length);
        System.out.println("表数据："+elementData);

    }


    public void get(){

        for(int i = 0 ,size = elementData.length; i < size ; i ++ ){

           // if(elementData[i]!=null){
                System.out.println("索引："+ i + " 链表："+elementData[i]);
           // }
        }
    }

    @Override
    public String toString() {
        return "HashTable{" +
                "factor=" + factor +
                ", DEFAULT_CAPACITY=" + DEFAULT_CAPACITY +
                ", elementData=" + Arrays.toString(elementData) +
                ", DEFAULT_EMPTY_CAPACITY=" + Arrays.toString(DEFAULT_EMPTY_CAPACITY) +
                '}';
    }
}
