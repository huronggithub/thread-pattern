package com.hr.thread.tss;

import java.lang.ref.PhantomReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 11:35
 */
public class ThreadLocalWeekReferenceTest {

    public static void weekReferenceUse(){
        ThreadLocal threadLocal=new ThreadLocal();
        byte[] bt=new byte[1024*2];
        threadLocal.set(bt);
        System.out.println(threadLocal.get());
    }

    /**
     * -Xms20m -Xmx20m -XX:+PrintGCDetails
     * @param args
     */
    public static void main(String[] args) {
        ThreadLocalWeekReferenceTest.weekReferenceUse();
        Thread thread = Thread.currentThread();
        List<Byte[]> bts=new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            bts.add(new Byte[1024*1]);
        }
        System.gc();
        thread = Thread.currentThread();
        System.out.println(thread);
    }
}