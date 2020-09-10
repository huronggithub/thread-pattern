package com.hr.thread.future;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/10 0010 下午 6:24
 */
public class RealData implements VirtualData<Integer> {

    private Integer rawValue=0;
    private int counts;

    public RealData(int counts) {
        this.counts = counts;
        for (int i = 0; i < counts; i++) {
            rawValue+=i;
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("初始化完毕了");
    }

    @Override
    public Integer get() {
        return rawValue;
    }
}