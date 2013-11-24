package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
//@Service("sendMMSThread")
public class SendMMSThread {
    @Autowired
    public TaskExecutor taskExecutor;

    public void submitMMS(){
        /*判断线程是否还在运行*/
        int n = Thread.activeCount();
        Thread[] threads = new Thread[n];
        Thread.enumerate(threads);
        boolean b = false;
        for (int i = 0; i < threads.length; i++) {
            Thread thread = threads[i];
            if (thread.getName().equals("sendmmsthread")) {
                b = true;
                break;
            }
        }

        if (!b){
            taskExecutor.execute(new SendMMSRunable("sendmmsthread"));
        }


    }
}
