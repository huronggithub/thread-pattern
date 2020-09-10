package com.hr.thread.future;

import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/10 0010 下午 7:37
 */
public class Host {
    public VirtualData<Integer> request(int c){
        FutureData futureData=new FutureData();
        new Thread(()->{
            futureData.setData(new RealData(c));
        }).start();
        return futureData;
    }

    public static void main(String[] args) {
        Host host=new Host();
        VirtualData<Integer> f = host.request(10);
        System.out.println(f.get().intValue());
    }
}