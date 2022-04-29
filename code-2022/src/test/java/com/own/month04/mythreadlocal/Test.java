package com.own.month04.mythreadlocal;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Test {
    static final int SIZE = 1;
    static final List<MyThreadLocal<String>> LOCALS;

    static {
        LOCALS = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            LOCALS.add(new MyThreadLocal<>());
        }
    }

    public static void main(String[] args) {
        new MyThread(TASK, "thread-1").start();
        new MyThread(TASK, "thread-2").start();
    }

    static final Runnable TASK = new Runnable() {
        @SneakyThrows
        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            for (int i = 0; i < 16; i++) {
                switch (operation()) {
                    case 1 -> set(LOCALS.get(0), name, UUID.randomUUID().toString());
                    case 2 -> get(LOCALS.get(0), name);
                }

                TimeUnit.SECONDS.sleep(2);
            }
        }
    };

    static int operation() {
        return (int)(Math.random() * 4 + 1);
    }

    static int index() {
        return (int)(Math.random() * SIZE);
    }

    static <T> void get(MyThreadLocal<T> local, String name) {
        System.out.println("get" + " -> " + name + " -> " + local.get());
    }

    static <T> void set(MyThreadLocal<T> local, String name, T obj) {
        System.out.println("set" + " -> " + name);
        local.set(obj);
    }

    static <T> void remove(MyThreadLocal<T> local, String name) {
        System.out.println("remove" + " -> " + name);
        local.remove();
    }

    static <T> void clear(MyThreadLocal<T> local, String name) {
        System.out.println("clear" + " -> " + name);
        local.clear();
    }
}
