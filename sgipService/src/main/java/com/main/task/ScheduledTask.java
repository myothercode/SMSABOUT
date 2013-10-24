package com.main.task;

import com.sgip.domain.QueueAndPools;
import com.sgip.domain.VO.SMSBody;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-18
 * Time: 下午10:30
 * To change this template use File | Settings | File Templates.
 */
public class ScheduledTask {
    public void getSmsFronDB() throws Exception{
        System.out.println("put sms............");
        QueueAndPools.smsQueue.put(new SMSBody());
       // throw new RuntimeException();

    }
}
