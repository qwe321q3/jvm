package com.jvm.algorithm;

/**
 * @ClassName : YsfCompute1
 * @Description : 约瑟夫杀人算法，基于单向循环链表，假设有n个人，当时报数到m时，m位置的人的退出，直到只剩下一个人
 * @Author : tianshuo
 * @Date: 2020-07-20 10:51
 */
public class YsfCompute1<E> {

    /**
     * 初始化人数
     */
    private int capacity;

    /**
     * 开始临界点
     */
    private int start;

    public YsfCompute1(int capacity, int start) {
        this.capacity = capacity;
        this.start = start;
    }

    private Node<E> head;


    /**
     * 构建循环链表
     *
     * @param node
     */
    private void add(Node<E> node) {
        if (head == null) {
            head = node;
            node.next = head;
        } else {
            /**
             * 数据加到的最后一个节点上
             */
            Node<E> last = head;
            while (true) {
                if (last.next == head) {
                    break;
                }
                last = last.next;
            }

            last.next = node;
            node.next = head;
        }
    }

    /**
     * 传入数字，在这个数字的点，开始杀人退出
     *
     * @param start 开始杀人的点
     * @return
     */
    private Node<E> begin(int start) {

        int index = 1;
        Node<E> node = head;
        Node<E> prev = null;
        for (; ; ) {
            prev = node;
            if (index == (start - 1)) {
                removeNext(node);
                index = 1;
            } else {
                index++;
            }

            if (node == node.next) {
                return node;
            }
            node = node.next;
        }
    }

    /**
     * 删除元素
     */
    private void removeNext(Node<E> node) {
        System.out.println("node: " + node.next);
        node.next = node.next.next;

    }


    /**
     * 初始化人数，然后传递临界点，到了临界点人退出，最后剩下一个人
     *
     * @return
     */
    public E kill() {

        for (int i = 1; i <= capacity; i++) {
            Node<E> node = new Node(i);
            add(node);
        }
        return begin(start).item;
    }


    private static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }

        public Node(E item) {
            this.item = item;
        }

        public Node() {
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }

    public static void main(String[] args) {
        YsfCompute1 ysfCompute1 = new YsfCompute1(6, 5);
        System.out.println(ysfCompute1.kill());
    }
}

