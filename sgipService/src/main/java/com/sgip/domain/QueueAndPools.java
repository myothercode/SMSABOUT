package com.sgip.domain;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: chace.cai
 * Date: 13-10-16
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class QueueAndPools {
    /*@Value("${thread.PoolNum}")
    public int threadPoolNum;*/

    public static ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(1);//定时获取数据的线程
    public static ExecutorService pool= Executors.newFixedThreadPool(5);//执行任务的线程
    public static AtomicInteger atomicInteger = new AtomicInteger(1);//计数器
}
