package com.sgip.comm.service.impl;

import com.sgip.comm.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-4
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates. eeee
 */
@Service("testServiceImpl")
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println("jjjjj");
    }
}
