package com.hr.thread.tpm;

/**
 * @Description: 每个消息一个线程
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 7:30
 */
public class Host {
    private static final Helper helper=new Helper();

    public void request(int i){
        System.out.println("start task"+i);
        new Thread(()->{
            helper.handle(i);
        }).start();
        System.out.println("end task"+i);
    }

    public static void main(String[] args) {
        Host host=new Host();
        for (int i = 0; i < 10; i++) {
            host.request(i);
        }
    }
}