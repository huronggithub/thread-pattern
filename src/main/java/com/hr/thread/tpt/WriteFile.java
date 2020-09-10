package com.hr.thread.tpt;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 二阶段终止
 * @Author: hurong
 * @Time: 2020/9/9 0009 下午 11:12
 */
public class WriteFile extends Thread {
    boolean interrupted;

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    @Override
    public void run() {
        while(!interrupted){
            doWork();
        }
        doShutdown();
    }

    private void doShutdown() {
        System.out.println("把文件刷入磁盘");
    }

    private void doWork() {
        System.out.println("把内容写入文件"+System.currentTimeMillis());
    }

    public static void main(String[] args) throws InterruptedException {
        WriteFile writeFile=new WriteFile();
        writeFile.start();
        TimeUnit.SECONDS.sleep(2);
        writeFile.setInterrupted(true);
    }
}