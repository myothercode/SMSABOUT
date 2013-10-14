package com.service;

import com.domainVO.LoginVo;
import com.domainVO.SessionVo;
import com.domainVO.SmsBody;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-11
 * Time: 下午8:23
 * To change this template use File | Settings | File Templates.
 */
public interface DataAccessService {
    public void insertSend(SmsBody smsBody);
    public SessionVo getLoginVo(LoginVo loginVo);
}
