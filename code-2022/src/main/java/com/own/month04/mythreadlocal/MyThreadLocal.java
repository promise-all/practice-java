package com.own.month04.mythreadlocal;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.Arrays;

/**
 * 自定义 ThreadLocal
 */
public class MyThreadLocal<T> {
    /**
     * 下一个 MyThreadLocal 的 index
     * 每个 ThreadLocal 独有一个 index
     */
    private static int nextIndex = 0;
    /**
     * 变量句柄
     * 用于提供以 volatile、synchronized、CAS 等方式操作变量的能力
     */
    private static VarHandle nextIndexHandle;
    /**
     * MyThreadLocal 的索引，用于唯一标志一个 MyThreadLocal
     */
    private int index;

    // 用于获取变量句柄
    static {
        try {
            nextIndexHandle = MethodHandles.lookup().findStaticVarHandle(MyThreadLocal.class, "nextIndex", int.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyThreadLocal() {
        index = index();
    }

    @SuppressWarnings("unchecked")
    public T get() {
        Thread thread = Thread.currentThread();
        if (thread instanceof MyThread myThread) {
            Object obj = myThread.context.get(index);
            try {
                return ((T) obj);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    public void set(T obj) {
        Thread thread = Thread.currentThread();
        if (thread instanceof MyThread myThread) {
            myThread.context.set(index, obj);
        }

    }

    public void remove() {
        Thread thread = Thread.currentThread();
        if (thread instanceof MyThread myThread) {
            myThread.context.set(index, null);
        }
    }

    public void clear() {
        Thread thread = Thread.currentThread();
        if (thread instanceof MyThread) {
            ((MyThread) thread).context.clear();
        }
    }

    /**
     * 获取递增的 index
     */
    private static int index() {
        if (null != nextIndexHandle) {
            return (int) nextIndexHandle.getAndAdd(1);
        } else {
            System.out.println("nextIndexHandle is null");
            synchronized (MyThreadLocal.class) {
                return ++nextIndex;
            }
        }
    }

    /**
     * 变量容器
     */
    public static class LocalContainer {
        private Object[] objects = new Object[2];
        /**
         * 默认容量为 2
         */
        private int capacity = 2;

        private boolean set(int index, Object newObj) {
            if (index >= objects.length && !grow(index)) {
                return false;
            }

            objects[index] = newObj;
            return true;
        }

        private Object get(int index) {
            if (index < 0 || index >= objects.length) {
                return null;
            }

            return objects[index];
        }

        private void clear() {
            Arrays.fill(objects, 0, objects.length, null);
        }

        /**
         * 数组扩容
         */
        private boolean grow(int targetCapacity) {
            if (targetCapacity == Integer.MAX_VALUE >> 1) {
                return false;
            }

            int size = objects.length;
            while (size <= targetCapacity) {
                size <<= 1;
            }

            Object[] newObjects = new Object[size];
            System.arraycopy(objects, 0, newObjects, 0, objects.length);
            objects = newObjects;

            return true;
        }
    }
}
