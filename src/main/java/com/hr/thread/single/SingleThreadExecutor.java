package com.hr.thread.single;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 所有线程一个个排队通过(gate.pass)
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 10:21
 */
public class SingleThreadExecutor extends Thread{
    private static final Gate gate=new Gate();

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            gate.pass();
        }
    }

    static class Gate {
        // 加锁，排队一个个过
        public synchronized void pass(){
            System.out.println(Thread.currentThread().getName()+" enter at:"+System.currentTimeMillis());
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+" leave at:"+System.currentTimeMillis());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threadList=new ArrayList();
        for (int i = 0; i <5 ; i++) {
            SingleThreadExecutor singleThreadExecutor = new SingleThreadExecutor();
            threadList.add(singleThreadExecutor);
            singleThreadExecutor.start();
        }

        for (Thread th:threadList) {
            th.join();
        }
    }
}