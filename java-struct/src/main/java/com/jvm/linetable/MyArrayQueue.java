package com.jvm.linetable;

/**
 * @ClassName : MyArrayQueue
 * @Description : 基于数据实现一个先进先出的队列
 * 1、基于数组实现的链表，使用head，tail2个索引指针来完成链表的移动
 * 2、当链表数据还是指针已经到最后，但是的链表的中还有空余一直时，移动链表
 * 3、当链表的已满时，提示链表已满
 * @Author : tianshuo
 * @Date: 2020-07-20 18:04
 */
public class MyArrayQueue<E> {

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

    public MyArrayQueue(int capacity) {
        this.capacity = capacity;
        this.objectElement = new Object[this.capacity];
    }


    public MyArrayQueue() {
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
        //队列已经到了末尾
        if (tail == capacity) {

            if (size >= capacity) {
                System.out.println("队列已满！");
                return;
            }
            //判断队列是否已经满了,如果tail - head > 0 说明队列还没有满可以移动位置
            if (tail - head > 0) {
                move();
            }
        }
        size++;
        objectElement[tail++] = e;
    }

    public E pop() {

        if (head >= objectElement.length) {
            System.out.println("越界");
            return null;
        }
        E e = (E) objectElement[head];
        //gc
        objectElement[head++] = null;
        size--;
        return e;
    }

    /**
     * 移动位置
     * 重新计算head 和 tail 2个索引指针。
     */
    private void move() {

        Object[] temp = new Object[capacity];
        /**
         * 使用size计算会更加合理且简单
         */
        for (int i = 0; i < capacity; i++) {
            if ((head + i) < capacity) {
                temp[i] = objectElement[head + i];
            } else {
                break;
            }
        }
        objectElement = temp;
        temp = null;
        tail = tail - head;
        head = 0;
    }

    /**
     * 扩容
     */
    private void grow() {

    }


    public static void main(String[] args) {
        MyArrayQueue myArrayQueue = new MyArrayQueue(4);
        for (int i = 0; i < 5; i++) {
            myArrayQueue.push(i);
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(myArrayQueue.pop());
        }

        myArrayQueue.push("6");

        System.out.println(myArrayQueue.size);
    }

}



