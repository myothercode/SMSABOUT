package com.domainVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: 下午5:08
 * To change this template use File | Settings | File Templates.
 */
public class QuenueVo {
    public final static BlockingQueue<MM7TableVO> smsQueue = new ArrayBlockingQueue<MM7TableVO>(30);//待发送的短信队列

}
