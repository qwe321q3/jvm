package com.jvm.linetable;

/**
 * @Classname MyStack
 * @Description 自定义栈实现，基于Array   ,  模拟运算
 * @Date 2020/7/19 0019 19:54
 * @Created by Administrator
 */
public class MyStack<E> {

    private E objectElement[];

    private int size;


    private int capacity;


    public MyStack(int capacity) {
        this.capacity = capacity;
        objectElement = (E[]) new Object[capacity];
    }

    public MyStack() {
        this(10);
    }


    public E pop() {

     //   int len = size-1<0?0:size-1;
        int len = size -1;

        E e = peak();


        objectElement[len] = null;
        size--;
        return e;

    }

    public E peak() {

        int len = size-1<0?0:size-1;
//        int len = size -1;
        E e = objectElement[len];
        return e;

    }


    public void push(E e) {

        grow();

        objectElement[size++] = e;

    }

    private boolean checkRange() {

        if(size >= objectElement.length){
            return true;
        }
        return false;
    }

    private void grow() {
        if (checkRange()) {
            int newSize = size <<1;
            Object temp[] = new Object[newSize];

            for (int i = 0; i < objectElement.length; i++) {
                temp[i] = objectElement[i];
            }

            objectElement = (E[]) temp;
        }
    }


    public int size() {
        return objectElement.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        MyStack<Integer> myStack = new MyStack<>();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
        myStack.push(5);

       while(myStack.peak()!=null){
           System.out.println(myStack.pop());
       }


    }

}
