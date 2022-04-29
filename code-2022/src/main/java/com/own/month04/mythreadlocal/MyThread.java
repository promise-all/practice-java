package com.own.month04.mythreadlocal;

public class MyThread extends Thread{
    MyThreadLocal.LocalContainer context;

    public MyThread(Runnable target, String name) {
        super(target, name);
        context = new MyThreadLocal.LocalContainer();
    }
}
