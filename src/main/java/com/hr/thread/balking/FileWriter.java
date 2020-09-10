package com.hr.thread.balking;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: hurong
 * @Time: 2020/9/8 0008 下午 11:35
 */
public class FileWriter {
    private StringBuilder sb;
    private boolean changed;

    public FileWriter() {
        this.sb = new StringBuilder();
    }

    public void write(int s){
        sb.append(s);
        changed=true;
        System.out.println("文件写入新的内容"+s);
    }

    public void save(){
        // 不需要就算了
        if(!changed){
            System.out.println("文件未改变");
            return;
        }
        // todo:把文件写入磁盘
        changed=false;
        System.out.println("文件刷入磁盘");
    }

    static class ChangeThead extends Thread{
        private FileWriter fileWriter;

        public ChangeThead(FileWriter fileWriter) {
            this.fileWriter = fileWriter;
        }

        @Override
        public void run() {
            for (int i = 0;; i++) {
                fileWriter.write(i);
                fileWriter.save();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class DaemonThead extends Thread{
        private FileWriter fileWriter;

        public DaemonThead(FileWriter fileWriter) {
            this.fileWriter = fileWriter;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    fileWriter.save();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FileWriter fileWriter=new FileWriter();
        new DaemonThead(fileWriter).start();
        new DaemonThead(fileWriter).start();
        new ChangeThead(fileWriter).start();
    }
}