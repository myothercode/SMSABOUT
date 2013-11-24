package com.service;

import com.domainVO.*;

import java.util.List;

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
    public void insertActive(ActiveVo activeVo);

    /**批量插入短信-*/
    public int batchSendSMS(final List<SmsBody> smsBodyList);
    public int insertSmsByGroup(SmsBody smsBody);

    int batchSendMMS(List<MM7TableVO> mm7TableVOList);
}
