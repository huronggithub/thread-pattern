package com.hr.thread.tpm;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 7:31
 */
public class Helper {
    public void handle(int i){
        System.out.println(Thread.currentThread().getName()+" 消费 "+i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}