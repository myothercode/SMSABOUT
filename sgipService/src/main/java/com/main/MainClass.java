package com.main;

import com.main.task.SubmitSMS;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.TaskExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-18
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */

public class MainClass {
    private static Log log = LogFactory.getLog(MainClass.class);
    public static ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    private static TaskExecutor taskExecutor;

    public static void main(String[] args) {
        log.info("hello commons-logging!");
        taskExecutor=applicationContext.getBean("taskExecutor",TaskExecutor.class);
        taskExecutor.execute(new SubmitSMS());


    }



}
