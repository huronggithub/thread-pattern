package com.hr.thread.pc;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 11:53
 */
public class PC {
    /**
     * 自定义数组队列，尾部入队，头部出队
     */
    public static class MyQueue {
        private final int[] datas;
        private int tail,top,count;

        public MyQueue(int size) {
            this.datas = new int[size];
        }

        public synchronized void put(int s) throws InterruptedException {
            while(datas.length==count){
                this.wait();
            }
            count++;
            datas[top]=s;
            top=(top+1)%datas.length;
            this.notifyAll();
        }

        public synchronized int take() throws InterruptedException {
            while(0==count){
                this.wait();
            }
            count--;
            int data = datas[tail];
            tail=(tail+1)%datas.length;
            this.notifyAll();
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
                try {
                    queue.put(i);
                    System.out.println("生产("+i+") at "+System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
                    System.out.println("消费("+queue.take()+") at "+System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        MyQueue queue=new MyQueue(10);
        Consumer consumer=new Consumer(queue);
        consumer.start();

        Consumer consumer2=new Consumer(queue);
        consumer2.start();

        Producer producer=new Producer(queue);
        producer.start();
    }

}