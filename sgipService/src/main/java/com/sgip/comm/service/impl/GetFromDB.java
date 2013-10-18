package com.sgip.comm.service.impl;

import com.sgip.domain.QueueAndPools;
import com.sgip.domain.VO.SMSBody;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-18
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope("prototype")
public class GetFromDB implements Runnable{
    @Override
    public void run() {
        System.out.println("springtest");
    }





   /* public void testLanckTimer(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
                throw new RuntimeException();
            }
        } ;
        QueueAndPools.scheduExec.scheduleWithFixedDelay(runnable,5,15, TimeUnit.SECONDS);
    }

    public void getSMS(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                SMSBody s=new SMSBody();
                s.setInnum(9L);
                s.setMobile_no("1355032");
                System.out.println("sms");
                try {
                    QueueAndPools.smsQueue.put(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } ;
        QueueAndPools.scheduExec.scheduleWithFixedDelay(runnable,5,10,TimeUnit.SECONDS);
    }*/
}
