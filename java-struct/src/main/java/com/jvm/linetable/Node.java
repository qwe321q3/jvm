package com.jvm.linetable;

/**
 * 链表结构
 * @param <T>
 */
public class Node<T> {

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
