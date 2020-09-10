package com.hr.thread.inmmutable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 因为对象不可改变(成员变量为final修饰，同时不提供set方法)，
 * 故即使多个线程同时访问，也无法对其进行修改，故其状态是一致，免去了加锁
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 10:32
 */
public class Inmmutable extends Thread {
    private Person p;

    public Inmmutable(Person p) {
        this.p = p;
    }

    @Override
    public void run() {
        try {
            while(true){
                System.out.println(p.toString());
                TimeUnit.MILLISECONDS.sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" finished");
    }

    static class Person{
        /**
         * 成员变量不可变，自然就没必要提供修改方法
         */
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(new Inmmutable(new Person("james",30)));
        Person blus = new Person("blus", 10);
        executorService.submit(new Inmmutable(blus));
        executorService.execute(new Inmmutable(blus));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println(executorService.isTerminated());
        System.out.println(executorService.isShutdown());
    }

}