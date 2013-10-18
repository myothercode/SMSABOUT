package com.sgip.domain;

import com.sgip.domain.VO.SMSBody;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: chace.cai
 * Date: 13-10-16
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class QueueAndPools {
    public static ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(2);//定时获取数据的线程
    public static ExecutorService pool= Executors.newFixedThreadPool(5);//执行任务的线程
    public final static BlockingQueue<SMSBody> smsQueue = new ArrayBlockingQueue<SMSBody>(3);//待发送的短信队列
    public static AtomicInteger atomicInteger = new AtomicInteger(1);//计数器
}
