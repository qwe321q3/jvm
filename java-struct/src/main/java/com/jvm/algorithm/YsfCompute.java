package com.jvm.algorithm;

/**
 * @Classname YsfCompute
 * @Description 约瑟夫算法  规定数据n个数据，报数到m时，退出，直到只剩下一个人.
 * @Date 2020/7/19 0019 15:10
 * @Created by Administrator
 */
public class YsfCompute<E> {


    /**
     * 总人数
     */
    private int count;

    /**
     * 阀值点
     */
    private int threshold;


    private Node<E> head;
    private Node<E> tail;


    public YsfCompute() {

    }

    public Node<E> begion(int count,int threshold){
        for (int i = 1; i <= count; i++) {
            add(i);
        }


        return  kill(threshold);
    }


    /**
     * 添加数据
     *
     * @param e
     */
    private void add(int e) {
        Node<E> node = new Node(e);

        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
            tail.next = head;
            head.prev = tail;
        }

    }

    /**
     * 开始杀人
     *
     * @param threshold
     */
    public Node<E> kill(int threshold) {

        int i = 1;
        Node<E> node = head;
        while (null != node) {

            if (threshold == i) {
                System.out.println("要被删除的节点："+node);

                remove(node);
                i = 1;
            } else {
                i++;
            }

            if (node.next == node) {
                return node;
            }

            node = node.next;
        }

        return null;

    }

    /**
     * 删除元素
     *
     * @param node 参数为要删除的节点的上一个节点
     */
    private void remove(Node<E> node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
    }

    private static class Node<E> {
        private Node<E> prev;
        private Node<E> next;
        private E item;

        public Node(Node<E> next, E item) {
            this.next = next;
            this.item = item;
        }

        public Node(E item) {
            this.item = item;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "item=" + item +
                    '}';
        }
    }

    public static void main(String[] args) {
//        要被删除的节点：Node{item=5}
//        要被删除的节点：Node{item=4}
//        要被删除的节点：Node{item=6}
//        要被删除的节点：Node{item=2}
//        要被删除的节点：Node{item=3}
//        Node{item=1}

        YsfCompute ysfCompute = new YsfCompute();

        System.out.println(ysfCompute.begion(6, 5));

    }


}
