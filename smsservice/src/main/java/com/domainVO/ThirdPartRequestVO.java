package com.domainVO;

import java.util.List;

/**
 * Created by chace.cai on 2014/6/30.
 */
public class ThirdPartRequestVO {
    /**短信平台信息编号。为2中的MO_MESSAGE_ID*/
    private String bstrMoMessageID;
    /**业务编码*/
    private String bstrBusinessCode;
    /**长代码(1：同步手机号码、3：计费通知)固定值为3*/
    private String bstrLongCode;
    /**付费号码*/
    private String bstrFeeMsisdn;
    /**目标号码*/
    private String bstrDesMsisdn;
    /**短信内容*/
    private String bstrMessageContent;

    private List<String> strList;


    public String getBstrBusinessCode() {
        return bstrBusinessCode;
    }

    public void setBstrBusinessCode(String bstrBusinessCode) {
        this.bstrBusinessCode = bstrBusinessCode;
    }

    public String getBstrLongCode() {
        return bstrLongCode;
    }

    public void setBstrLongCode(String bstrLongCode) {
        this.bstrLongCode = bstrLongCode;
    }

    public String getBstrFeeMsisdn() {
        return bstrFeeMsisdn;
    }

    public void setBstrFeeMsisdn(String bstrFeeMsisdn) {
        this.bstrFeeMsisdn = bstrFeeMsisdn;
    }

    public String getBstrDesMsisdn() {
        return bstrDesMsisdn;
    }

    public void setBstrDesMsisdn(String bstrDesMsisdn) {
        this.bstrDesMsisdn = bstrDesMsisdn;
    }

    public String getBstrMessageContent() {
        return bstrMessageContent;
    }

    public void setBstrMessageContent(String bstrMessageContent) {
        this.bstrMessageContent = bstrMessageContent;
    }

    public String getBstrMoMessageID() {
        return bstrMoMessageID;
    }

    public void setBstrMoMessageID(String bstrMoMessageID) {
        this.bstrMoMessageID = bstrMoMessageID;
    }

    public List<String> getStrList() {
        return strList;
    }

    public void setStrList(List<String> strList) {
        this.strList = strList;
    }
}
