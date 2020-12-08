package com.jvm.dp.chain.responsibility.standard;

/**
 * 处理人
 */
public abstract class Handler<T> {

    /**
     * 继任者
     */
    protected Handler successor;

    /**
     * 设置处理人
     * @param handler
     */
    public abstract void setHandler(Handler handler);

    /**
     * 处理信息
     * @param t
     */
    public abstract void handleRequest(T t);
}
