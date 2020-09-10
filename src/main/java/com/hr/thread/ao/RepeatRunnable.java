package com.hr.thread.ao;

/**
 * @Description: 实现可重复的任务
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 10:04
 */
public class RepeatRunnable implements Runnable {
    private static final ActiveObjectEngine engine=new ActiveObjectEngine();
    private static boolean stop;
    private long delay;
    private Runnable target;

    public RepeatRunnable(long delay, Runnable target) {
        this.delay = delay;
        this.target = target;
    }

    @Override
    public void run() {
        target.run();
        if(!stop){
            engine.addRunnable(new DelayRunnable(this,delay,engine));
        }
    }

    public static void main(String[] args) {
        engine.addRunnable(new RepeatRunnable(50,()->{
            System.out.println(System.currentTimeMillis()+"A");
        }));
        engine.addRunnable(new RepeatRunnable(100,()->{
            System.out.println(System.currentTimeMillis()+"B");
        }));
        engine.addRunnable(new DelayRunnable(()->{
            stop=true;
        },2000,engine));
        engine.execute();
    }
}