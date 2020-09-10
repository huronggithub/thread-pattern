package com.hr.thread.gs;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 自定义队列，尾部入头部出
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 11:07
 */
public class MyQueue {
    private final LinkedList<String> datas;

    public MyQueue() {
        this.datas = new LinkedList();
    }

    public synchronized String get() throws InterruptedException {
        // 不具备条件，则等待，直到条件满足后继续执行
        while(datas.size()==0){
            this.wait();
        }
        return datas.removeFirst();
    }

    public synchronized void put(String i) throws InterruptedException {
        datas.addLast(i);
        this.notifyAll();
    }

    static class ServerThread extends Thread{
        private final MyQueue queue;

        public ServerThread(MyQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while(true){
                    System.out.println(Thread.currentThread().getName()+"消费"+queue.get()+","+System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ClientThread extends Thread{
        private final MyQueue queue;

        public ClientThread(MyQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put(Thread.currentThread().getName()+i);
                    System.out.println(Thread.currentThread().getName()+"生产"+Thread.currentThread().getName()+i+","+System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyQueue queue=new MyQueue();
        for (int i = 0; i < 5; i++) {
            new ServerThread(queue).start();
        }

        TimeUnit.MILLISECONDS.sleep(1000);
        for (int i = 0; i < 5; i++) {
            new ClientThread(queue).start();
        }
    }
}