package com.controller;

import com.domainVO.LoginVo;
import com.domainVO.SessionVo;
import com.domainVO.SmsBody;
import com.service.DataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-9
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SmsController{
    @Autowired
    private DataAccessService dataAccessService;

    public static final String SESSION_KEY="system_session_sms_11111";

    /*登录*/
    @RequestMapping("/login/loginUser")
    public String loginUser(HttpServletRequest request,LoginVo loginVo){
        if(loginVo==null||loginVo.getUserId()==null||loginVo.getPassWord()==null)return "login";
        SessionVo sessionVo = dataAccessService.getLoginVo(loginVo);
        if(sessionVo==null||sessionVo.getId()==null||sessionVo.getId()==0)return "login";
        HttpSession session = request.getSession(false);

        /*SessionVo sessionVo =new SessionVo() ;
        sessionVo.setId(88L);*/
        session.setAttribute(SESSION_KEY,sessionVo);
        return "mainPage";
    }

    @RequestMapping("/sms/mainPage")
    public String mainPage(){

        return "mainPage";
    }

    @RequestMapping("/sms/smsPage")
     public String smsPage(){
        return "smsPage";
     }

    /*发送短信*/
    @RequestMapping(value = "/sms/sendSMS")
    @ResponseBody
    public Object sendSMS(HttpSession session,SmsBody smsBody){
        if(session==null)return "请到登录页面登录！";
        SessionVo sessionVo =(SessionVo) session.getAttribute(SESSION_KEY);
        if(sessionVo.getId()==null || sessionVo.getId()==0)return "请到登录页面登录！";
        smsBody.setMsg("".equals(smsBody.getMsg())?null:smsBody.getMsg());
        smsBody.setPhoneNo("".equals(smsBody.getPhoneNo())?null:smsBody.getPhoneNo());
        smsBody.setServiceId("".equals(smsBody.getServiceId())?null:smsBody.getServiceId());
        smsBody.setServiceId("106289975");
        Assert.notNull(smsBody.getServiceId(),"服务号为空!");
        Assert.notNull(smsBody.getPhoneNo(),"号码为空!");
        Assert.notNull(smsBody.getMsg(),"内容为空!");

        smsBody.setUserId(sessionVo.getId());
        dataAccessService.insertSend(smsBody);
        return "ok" ;
    }
}
