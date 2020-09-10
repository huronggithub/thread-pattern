package com.hr.thread.ao;

/**
 * @Description: 实现延时任务
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 9:48
 */
public class DelayRunnable implements Runnable {
    private boolean start;
    private long startTime;
    private Runnable target;
    private long delay;
    private ActiveObjectEngine engine;

    public DelayRunnable(Runnable target, long delay, ActiveObjectEngine engine) {
        this.target = target;
        this.delay = delay;
        this.engine = engine;
    }

    @Override
    public void run() {
        if(!start){
            // 标记开始时间
            start=true;
            startTime=System.currentTimeMillis();
            // 自己再次入队
            engine.addRunnable(this);
        }else if (System.currentTimeMillis()-startTime<delay){
            // 未见未到，把自己再次放到队列
            engine.addRunnable(this);
        }else{
            // 时间到了，把真正的任务放到队列
            engine.addRunnable(target);
        }
    }

    public static void main(String[] args) {
        ActiveObjectEngine engine=new ActiveObjectEngine();
        DelayRunnable delayRunnable=new DelayRunnable(()->{
            System.out.println("execute at "+System.currentTimeMillis());
        },1000,engine);
        engine.addRunnable(delayRunnable);
        System.out.println("  start at "+System.currentTimeMillis());
        engine.execute();
    }
}