package com.jvm;

import java.util.Arrays;

public class MyArrayList<T> implements List<T>{

    private Object[] elementData;

    private final Object[] DEFAULT_EMPTY_CAPACITY = new Object[0];

    private final int DEFAULT_CAPACITY = 3;

    /**
     * 数据组中元素个数
     */
    private int size;

    public MyArrayList(){
        this(0);
    }

    public MyArrayList(int capacity){
        if (capacity == 0){
            elementData = DEFAULT_EMPTY_CAPACITY;
        }else {
            elementData = new Object[capacity];
        }
    }

    /**
     * 添加元素
     * 1、判断数组是否已经存满，如果已经存满需要动态扩容，扩容规则为原数组的一半
     * 2、如果数据未满，则往数组中增加元素，同时改变当前数组的个数
     * @param t
     */
    @Override
    public void add(T t) {

//        grow();
//
//        elementData[size++] = t;

        add(size,t);
    }


    /**
     * 动态扩容
     */
    private void grow(){
        if(elementData.length <= size){
            int oldLength = elementData.length;
            int newLength = oldLength+(oldLength>>1);

            if(newLength - oldLength <=0){
                newLength = DEFAULT_CAPACITY;
            }

            elementData = Arrays.copyOfRange(elementData,0,newLength);
//            Object[]newArray = new Object[newLength];
//            for (int i = 0; i <oldLength ; i++) {
//                newArray[i] = elementData[i];
//            }
//            elementData = newArray;

            System.out.println(elementData.length);
        }
    }

    @Override
    public T get(int index) {
        if(index<0|| index>size){
            throw new RuntimeException("索引越界"+index);
        }

        return (T) elementData[index];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size==0;
    }

    @Override
    public boolean contain(T t) {
        return false;
    }

    @Override
    public void add(int index, T t) {

        checkRange(index);


        grow();

        for(int i = elementData.length-1; i>index ;i--){
            elementData[i]=elementData[i-1];
        }

        elementData[index] = t;
        size++;
    }

    /**
     * 校验索引范围
     * @param index
     */
    private  void checkRange(int index){
        if(index < 0 || index > size){
            throw new RuntimeException("索引越界"+index);
        }
    }

    /**
     * 1、删除索引位的元素，索引位后的其他元素都向前1位
     * @param index
     */
    @Override
    public void remove(int index) {

        checkRange(index);

        elementData[index] = null;

        for (int i = index,len= elementData.length-1;i<len ;i++) {
            elementData[i] = elementData[i+1];
        }

        if(index != elementData.length-1){
            elementData[elementData.length-1] = null;
        }

        size -- ;

    }

    @Override
    public void removeAll() {
        elementData = null;
        elementData = DEFAULT_EMPTY_CAPACITY;
    }

    @Override
    public String toString() {
        if(isEmpty()){
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0,len = elementData.length; i <len; i++){
            if(i==0){
                    sb.append(elementData[i]);
            }else {
                sb.append(",").append(elementData[i]);
            }
        }

        sb.append("]");
        return sb.toString();

    }
}
