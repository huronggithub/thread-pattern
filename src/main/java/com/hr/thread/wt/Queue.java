package com.hr.thread.wt;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 工作线程一直在不停的尝试进行消费
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 10:50
 */
public class Queue {
    private static final AtomicInteger integer=new AtomicInteger();
    private LinkedList<Integer> queue=new LinkedList<>();
    private LinkedList<Worker> workers=new LinkedList<>();

    public Queue(int workerCount) {
        for (int i = 0; i < workerCount; i++) {
            workers.add(new Worker(this));
        }
        for (int i = 0; i < workers.size(); i++) {
            workers.get(i).start();
        }
    }

    public synchronized void put(Integer i) throws InterruptedException {
        queue.addLast(i);
        this.notifyAll();
    }

    public synchronized Integer take() throws InterruptedException {
        while(queue.size()==0){
            this.wait();
        }
        Integer data = queue.removeFirst();
        this.notifyAll();
        return data;
    }

    class Worker extends Thread{
        private Queue queue;

        public Worker(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true){
                try {
                    System.out.println(Thread.currentThread().getName()+" 消费 "+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Client extends Thread{
        private Queue queue;

        public Client(Queue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    int x = integer.incrementAndGet();
                    queue.put(x);
                    System.out.println("生产"+x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Queue queue=new Queue(5);
        new Client(queue).start();
        new Client(queue).start();
        new Client(queue).start();
        TimeUnit.SECONDS.sleep(10);
    }
}