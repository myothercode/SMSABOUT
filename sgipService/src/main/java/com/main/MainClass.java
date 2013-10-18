package com.main;

import com.sgip.comm.service.TestService;
import com.sgip.comm.service.impl.GetFromDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-18
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */

public class MainClass {

    public static ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private TestService testService;

    public static void main(String[] args) {

        Object o= applicationContext.getBean("taskExecutor");

        MainClass mainClass=new MainClass();
        mainClass.runTask();
        //taskExecutor.execute(new GetFromDB());
        /*GetFromDB getFromDB=new GetFromDB();
        getFromDB.testLanckTimer();
        getFromDB.getSMS();*/
    }

    public void runTask(){
        testService.test();
        //taskExecutor.execute(new GetFromDB());
    }

}
