package com.hr.thread.future;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/10 0010 下午 6:25
 */
public class FutureData implements VirtualData<Integer> {

    private VirtualData<Integer> data;
    private boolean isReady;

    public FutureData(){
    }

    public synchronized void setData(VirtualData<Integer> data) {
        this.data = data;
        isReady=true;
        notifyAll();
    }

    @Override
    public synchronized Integer get(){
        while(!isReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data.get();
    }
}