package com.own.month04.mythreadlocal;

/**
 * 私有实现的 Thread
 * 区别点在于使用自定义 ThreadLocal
 */
public class MyThread extends Thread{
    /**
     * MyThreadLocal 容器
     */
    MyThreadLocal.LocalContainer context;

    public MyThread(Runnable target, String name) {
        super(target, name);
        context = new MyThreadLocal.LocalContainer();
    }
}
