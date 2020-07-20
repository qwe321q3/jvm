package com.jvm.linetable;

/**
 * @ClassName : MyArrayQueue
 * @Description : 基于数据实现一个先进先出的队列
 * @Author : tianshuo
 * @Date: 2020-07-20 18:04
 */
public class MyArrayQueue<E> {

    private Object[] objectElement;

    private int size;

    private int capacity;

    private boolean initFlag = false;

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
     * 如果的队列完了之后，从头还是基础使用。
     * @param e
     */
    public void push(E e) {

        if (initFlag && tail == objectElement.length) {
            //grow();
            System.out.println("队列已满！");
            return;
        }
        initFlag = true;
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
        return e;
    }

    /**
     * 扩容
     */
    private void grow() {

    }


    public static void main(String[] args) {
        MyArrayQueue myArrayQueue = new MyArrayQueue();
        for (int i = 0; i < 11; i++) {
            myArrayQueue.push(i);
        }

        for (int i = 0; i < 11; i++) {
            System.out.println(myArrayQueue.pop());
        }

    }

}



