package com.jvm.dp.template;

/**
 * 模版模式
 * 提供游戏方法类
 * 1、定义initial抽象方法
 * 2、选择人物
 *
 * 提供一定规则的模版方法，让子类按照模版要求实现方法，起到一定的限制作用
 */
public abstract class AbstractGame {
    public abstract void initial();

    public abstract void choosePerson();

    public final void play() {
        initial();
        choosePerson();
    }
}
