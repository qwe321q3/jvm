package com.jvm.linetable;

/**
 * 1、链表是由存储数据的单元组成，单元之间通过地址指向串联起来
 * 2、链表的每个结点都至少有2个域来组成，1.一个域用于数据存储 2.另外一个域是指向其他单元的指针
 * 单链表的会有一个哑元结点，也叫头结点
 * 头结点不存在任何数据，next执行0号元素
 * 头结点可以对空表，非空表的情况以及对首节点统一处理，编程更方便，常用头结点
 * @param <T>
 */
public class MyLinkedList<T> implements List<T> {

    private int size;

    private Node<T> head = new Node<>();

    @Override
    public void add(T t) {

        add(size,t);
    }

    @Override
    public T get(int index) {
        if(index<0 || index > size){
            throw new RuntimeException("索引越界"+index);
        }

        Node<T> p = head;

        for(int i = 0; i<=index; i ++){
            p = p.next;
        }
        return p.data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contain(T t) {
        Node<T> node = head;
        if(t == node.data){
            return true;
        }

        while((node=node.next)!=null){
            if(node.data==t){
                return true;
            }
        }

        return false;
    }

    @Override
    public void add(int index, T t) {
        if(index<0 || index > size){
            throw new RuntimeException("索引越界"+index);
        }

        Node<T> preNode = head;

        for(int i = 0; i<index; i ++){
            preNode = preNode.next;
        }

        Node<T>node = new Node<>(t);

        node.next = preNode.next;
        preNode.next = node;

        size ++ ;
    }

    @Override
    public void remove(int index) {
        Node<T>node = head.next;
        for(int i =0 ;i<index-1;i++){
            node = node.next;
        }

        node.next = node.next.next;

        size --;
    }

    @Override
    public void removeAll() {
        head = null;
        head = new Node<>();
        size = 0 ;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Node<T> node  = head.next;
        if(size > 0 ){
            sb.append(node.data);
        }
        for (int i = 0; i < size - 1; i++) {
            node = node.next;
            sb.append(",").append(node.data);
        }
        sb.append("]");

        return sb.toString();

    }
}

class Node<T> {
    T data;

    Node<T> next;

    Node() {
    }

    Node(T data) {
        this.data = data;
    }

    Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
