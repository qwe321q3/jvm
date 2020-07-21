package com.jvm.linetable;

/**
 * @ClassName : MyArrayQueue
 * @Description : 基于数据实现一个先进先出的循环队列
 * 1、基于数组实现的队列，使用head，tail2个索引指针来完成队列的移动，为了实现的循环链表
 * 2、当链表数据还是指针已经到最后，需要数据需要从0开始继续插入
 * 3、当队列的已满时，提示队列已满，也可以实现扩容方法
 * @Author : tianshuo
 * @Date: 2020-07-20 18:04
 */
public class MyCircleArrayQueue<E> {

    private Object[] objectElement;

    private int size;

    private int capacity;

    /**
     * 使用head来做数组的头指针
     */
    private int head;

    /**
     * tail来做数组的尾指针
     */
    private int tail;

    public MyCircleArrayQueue(int capacity) {
        this.capacity = capacity;
        this.objectElement = new Object[this.capacity];
    }


    public MyCircleArrayQueue() {
        this.objectElement = new Object[10];
    }

    /**
     * 目前push的时间复杂度为O(1)，在数据存放到的末尾的时候，此时需要移动数组数据，此时的时间复杂都为O(n)
     * 如果队列到了末尾之后的处理方式
     * <p>
     * 1、如果队列未满，让队列的数据往前面移动
     * 2、如果队列已经满了，a)不能添加 b) 扩容
     *
     * @param e
     */
    public void push(E e) {
        //队列是否已满
        if (size == capacity) {
//        if ((tail+1)%capacity==head){
            System.out.println("队列已满！");

        } else {
            objectElement[tail] = e;
            tail = (tail + 1) % capacity;
            System.out.println("tail：" + tail);
            size++;
        }

    }

    public E pop() {

        if (size==0) {
            System.out.println("队列为空！");
            return null;
        }
        E e = (E) objectElement[head];
        //gc
        objectElement[head] = null;
        head = (head + 1) % capacity;
        size--;
        return e;
    }



    public static void main(String[] args) {
        MyCircleArrayQueue myArrayQueue = new MyCircleArrayQueue(4);
        for (int i = 0; i < 5; i++) {
            myArrayQueue.push(i);
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(myArrayQueue.pop());
        }
//
        myArrayQueue.push("6");

        System.out.println(myArrayQueue.size);

        System.out.println(myArrayQueue.head + "  --  "+myArrayQueue.tail);
        myArrayQueue.push("8");
        System.out.println(myArrayQueue.head + "  --  "+myArrayQueue.tail);


    }

}



