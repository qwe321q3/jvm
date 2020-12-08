package com.jvm.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname LruCache  (least recently used)  最少最近使用缓存
 * @Description 插入O(1) 删除O(1) 查询O(1)
 * 1、选择双向链表及HashMap结合
 * 2、保持最近使用的数据都在头结点，超过缓存最大容量时，移除尾结点数据
 * @Date 2020/7/19 0019 11:52
 * @Created by tianshuo
 */
public class LruCache<K, V> {

    /**
     * lru缓存容量
     */
    private int capacity;


    /**
     * 保存缓存元素数量
     */
    private int size;

    /**
     * 保存的缓存元素
     * key：value
     */
    private Map<Object, LruNode<K, V>> lruMap;


    /**
     * 最近使用的数据都会放入头结点，
     * 也是就说使用较少的数据会一点点被移动到链表的末端
     */
    private LruNode<K, V> head;

    /**
     * 链表尾结点
     */
    private LruNode<K, V> tail;


    /**
     * 初始化容量
     *
     * @param capacity
     */
    public LruCache(int capacity) {
        this.capacity = capacity;
        lruMap = new HashMap<>(capacity);
    }


    /**
     * 校验是否到了缓存最大容量
     *
     * @return
     */
    private boolean checkRange() {

        return size >= capacity;
    }


    /**
     * 最近使用的数据放到链表的头部
     *
     * @param lruNode
     */
    private void addHead(LruNode lruNode) {

        //如果缓存已满，删除最后一个链表最尾端数据
        if (checkRange()) {
            removeLast();
        }

        if (head == null) {
           tail = lruNode;
        } else {
            //如果此判断的已经缓存中存在，先删除此元素在链表的位置
            if (lruMap.containsKey(lruNode.key)) {
                LruNode<K, V> oldNode = lruMap.get(lruNode.key);
                remove(oldNode);
                size -- ;
            }


            lruNode.next = head;
            head.prev = lruNode;


        }
        head = lruNode;

        size++;
    }


    /**
     * 删除节点
     * @param node
     */
    private void remove(LruNode<K, V> node) {
        if (node !=head){
            node.next.prev = node.prev;
        }else{
            head = head.next;
        }
        if (null!=node.next) {
            node.next.prev = node.prev;
        }
    }


    /**
     * 根据key删除节点
     * @param k
     */
    private void remove(K k) {
        if (lruMap.containsKey(k)) {
            LruNode<K, V> kvLruNode = lruMap.get(k);
            remove(kvLruNode);
            lruMap.remove(k);
            size -- ;
        }
    }


    /**
     * 移除链表尾端数据
     */
    private void removeLast() {

        lruMap.remove(tail.key);
        tail = tail.prev;
        tail.next=null;
        size--;


    }


    /**
     * 从缓存中获取数据
     * 同时更新缓存元素位置为头结点，继续保持数据热度
     *
     * @param key
     * @return
     */
    public V get(Object key) {

        LruNode<K, V> currentNode = lruMap.get(key);
        /**
         * 如果能取到元素，更新缓存位置
         * 1、删除元素原有位置
         * 2、重新把速度放入到头部
         */
        if (currentNode != null) {
            addHead(currentNode);
        }
        return currentNode.value;
    }


    /**
     * 1、判断数据在缓存中是否存在，如果存在更新缓存中的值
     * 2、从缓存中获取数据
     * 3、同时更新缓存元素位置为头结点，继续保持数据热度
     *
     * @param key
     * @return
     */
    public V put(K key, V value) {
        LruNode<K, V> lruNode;

        /**
         * 如果数据在缓存中已经存在，更新缓存中数据的值。
         */
        if (lruMap.containsKey(key)) {

            lruNode = lruMap.get(key);
            lruNode.value = value;

        } else {
            lruNode = new LruNode<>(key, value);
        }

        addHead(lruNode);

        lruMap.put(key, lruNode);
        return lruNode.value;
    }


    /**
     * 查询缓存数据大小
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断lru缓存是否为空
     * @return
     */
    public boolean isEmpty() {
        return size==0;
    }




    /**
     * 双向链表节点
     *
     * @param <K,V>
     */
    private static class LruNode<K, V> {
        /**
         * 前驱节点
         */
        private LruNode<K, V> prev;

        private K key;

        /**
         * 节点值
         */
        private V value;

        /**
         * 后继节点
         */
        private LruNode<K, V> next;

        public LruNode() {

        }

        public LruNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public LruNode(LruNode<K, V> prev, K key, V value, LruNode<K, V> next) {
            this.prev = prev;
            this.key = key;
            this.value = value;
            this.next = next;
        }

    }

    public static void print(LruCache lruCache) {
        LruNode node = lruCache.head;
        while(null!=node){
            System.out.println("node : "+node.key + " value: "+node.value);
            node = node.next;
        }
    }


    public static void main(String[] args) {
        LruCache lruCache = new LruCache(4);


        lruCache.put("a","1");
        lruCache.put("b","2");
//
        lruCache.put("c","3");
        print(lruCache);
        System.out.println("");
        lruCache.remove("c");
        System.out.println(" head : "+lruCache.head.key+" - "+lruCache.head.value +" 缓存长度："+lruCache.size);
        System.out.println(" cache : "+lruCache.lruMap);


        print(lruCache);



    }

}
