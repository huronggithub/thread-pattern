package com.hr.thread.rwl;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 11:53
 */
public class ReadWriteLockPC {
    /**
     * 尾部入队，头部出队
     */
    public static class MyQueue {
        private final LinkedList<Integer> datas;
        private final ReadWriteLock lock=new ReentrantReadWriteLock();
        private final Lock readLock=lock.readLock();
        private final Lock writeLock=lock.writeLock();
        private int count;

        public MyQueue(int size) {
            this.datas = new LinkedList<>();
        }

        public void put(int s) {
            writeLock.lock();
            datas.addLast(s);
            count++;
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            writeLock.unlock();
        }

        public int take() throws IllegalStateException{
            readLock.lock();
            if(count==0){
                readLock.unlock();
                throw new IllegalStateException("没有数据可以获取了");
            }
            int data = datas.removeFirst();
            count--;
            readLock.unlock();
            return data;
        }
    }

    static class Producer extends Thread{
        private MyQueue queue;

        public Producer(MyQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 1;i<=100; i++) {
                queue.put(i);
                System.out.println("生产("+i+") at "+System.currentTimeMillis());
            }
        }
    }

    static class Consumer extends Thread{
        private MyQueue queue;

        public Consumer(MyQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("消费(" + queue.take() + ") at " + System.currentTimeMillis());
                }catch (IllegalStateException e){
                    System.out.println("未消费任何数据 at " + System.currentTimeMillis());
                }
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        MyQueue queue=new MyQueue(10);
        Consumer consumer=new Consumer(queue);
        consumer.start();

        Producer producer=new Producer(queue);
        producer.start();

        consumer.join();
    }

}