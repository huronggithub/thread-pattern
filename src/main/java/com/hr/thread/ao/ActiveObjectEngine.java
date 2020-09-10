package com.hr.thread.ao;

import java.util.LinkedList;

/**
 * @Description: 执行引擎
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 9:52
 */
public class ActiveObjectEngine {
    private LinkedList<Runnable> queue=new LinkedList<>();

    /**
     * 添加到队列
     * @param runnable
     */
    public void addRunnable(Runnable runnable){
        queue.addLast(runnable);
    }

    public void execute(){
        while (!queue.isEmpty()){
            queue.removeFirst().run();
        }
    }

}