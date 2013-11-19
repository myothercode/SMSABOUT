package com.controller;

import com.common.utils.CommonUtil;
import com.common.utils.MMSfactory;
import com.domainVO.*;
import com.service.DataAccessService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-10-9
 * Time: 下午11:02
 * To change this template use File | Settings | File Templates.
 */
/**
 017
 * SpringMVC中的文件上传
 018
 * 1)由于SpringMVC使用的是commons-fileupload实现,所以先要将其组件引入项目中
 019
 * 2)在SpringMVC配置文件中配置MultipartResolver处理器(可在此加入对上传文件的属性限制)
 020
 * 3)在Controller的方法中添加MultipartFile参数(该参数用于接收表单中file组件的内容)
 021
 * 4)编写前台表单(注意enctype="multipart/form-data"以及<input type="file" name="****"/>)
 022
 * PS:由于这里使用了ajaxfileupload.js实现无刷新上传,故本例中未使用表单
*/
@Controller("smsController")
public class SmsController{
    @Autowired
    private DataAccessService dataAccessService;

    public static final String SESSION_KEY="system_session_sms_11111";

    /*登录**/
    @RequestMapping("/login/loginUser")
    public String loginUser(HttpServletRequest request,LoginVo loginVo){
        //setSession(request);
        if(loginVo==null||loginVo.getUserId()==null||loginVo.getPassWord()==null)return "login";
        SessionVo sessionVo = dataAccessService.getLoginVo(loginVo);
        if(sessionVo==null||sessionVo.getId()==null||sessionVo.getId()==0)return "login";
        if(!"1".equals(sessionVo.getRole()) ){return "login";}
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_KEY,sessionVo);
        return "mainPage";
    }
    @RequestMapping("/mms/mmsPage")
    public String mmsPage(){
        return "mmsPage";
    }
    @RequestMapping("/sms/mainPage")
    public String mainPage(){
        return "mainPage";
    }
    @RequestMapping("/sms/top")
    public String topPage(){
        return "files/top";
    }
    @RequestMapping("/sms/left")
    public String leftPage(){
        return "files/left";
    }
    @RequestMapping("/sms/mainfra")
    public String mainfra(){
        return "files/mainfra";
    }

    @RequestMapping("/sms/smsPage")
     public String smsPage(){
        return "smsPage";
     }


    @RequestMapping(value = "/sms/insertActive")
    @ResponseBody
    public Object insertActive(HttpSession session,ActiveVo activeVo){
        activeVo.setHdid("".equals(activeVo.getHdid())?null:activeVo.getHdid());
        activeVo.setMsg("".equals(activeVo.getMsg())?null:activeVo.getMsg());
        activeVo.setUser_id("".equals(activeVo.getUser_id())?null:activeVo.getUser_id());

        Assert.notNull(activeVo.getHdid(),"参数为空!");
        Assert.notNull(activeVo.getMsg(),"参数为空!");
        Assert.notNull(activeVo.getUser_id(),"参数为空!");

        dataAccessService.insertActive(activeVo);

        return "ok";
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

    /*批量那个发送短信*/
    @RequestMapping(value = "/sms/batchSendSMS")
    @ResponseBody
    public Object batchSendSMS(HttpSession session,@RequestParam("multipartFiles")MultipartFile[] multipartFiles,SmsBody smsBody,
                               HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(session==null)return "请到登录页面登录！";
        SessionVo sessionVo =(SessionVo) session.getAttribute(SESSION_KEY);
        if(sessionVo.getId()==null || sessionVo.getId()==0)return "请到登录页面登录！";

        String rsStr="err";
        if(multipartFiles!=null){
            for(MultipartFile multipartFile:multipartFiles){
                if(multipartFile.isEmpty()){
                    rsStr="请现在文件后上传";
                    continue;
                } else {

                    List<SmsBody> slist=new ArrayList<SmsBody>();
                    InputStream is=multipartFile.getInputStream();
                    InputStreamReader inputStreamReader=new InputStreamReader(is);
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    String linText=null;
                    while ((linText=bufferedReader.readLine())!=null){
                        if(linText!=null&&!"".equals(linText)&&linText.length()>=11){
                            linText=linText.trim();
                             if(linText.startsWith("1")&&linText.length()==11){
                                 SmsBody s=new SmsBody();
                                 s.setServiceId("106289975");
                                 s.setUserId(sessionVo.getId());
                                 //s.setUserId(888L);
                                 s.setPhoneNo("86"+linText);
                                 s.setMsg(smsBody.getMsg());
                                 s.setReserve("000000");
                                 slist.add(s) ;
                             }else if(linText.startsWith("8")&&linText.length()==13){
                                 SmsBody s=new SmsBody();
                                 s.setServiceId("106289975");
                                 s.setUserId(sessionVo.getId());
                                 //s.setUserId(888L);
                                 s.setPhoneNo(linText);
                                 s.setMsg(smsBody.getMsg());
                                 s.setReserve("000000");
                                 slist.add(s) ;
                             }
                        }

                    }
                    bufferedReader.close();
                    inputStreamReader.close();
                    dataAccessService.batchSendSMS(slist);
                    rsStr="ok";
                }
            }
        }
         return rsStr;
    }

    @RequestMapping(value = "/mms/mmsSend")
    @ResponseBody
    public Object mmsSend(HttpSession session,@RequestParam("multipartFiles")MultipartFile[] multipartFiles,SmsBody smsBody,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        String res="";
          if (multipartFiles!=null){
              MM7VO mm7VO=new MM7VO();
              mm7VO.setPhone(smsBody.getPhoneNo());
              mm7VO.setTitle("-");
              mm7VO.setYewuCode("3181213802");
              mm7VO.setYewuNum("106289974");
              Map map= CommonUtil.filterTxt(multipartFiles);    //过滤出图片文件

              List<AttVo> attVos=new ArrayList<AttVo>();

              for (MultipartFile multipartFile:multipartFiles){
                  if (multipartFile.isEmpty()){
                      res="没有上传文件";
                  }else {
                      if(multipartFile.getOriginalFilename().endsWith(".txt")){
                          InputStream inputStream = multipartFile.getInputStream();
                          byte [] bytes = new byte[inputStream.available()];
                          inputStream.read(bytes);
                          inputStream.close();

                          AttVo attVo=new AttVo();
                          MultipartFile txt=(MultipartFile)map.get(multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf(".")));
                          InputStream inputStreamtxt = txt.getInputStream();
                          byte [] bytestxt = new byte[inputStreamtxt.available()];
                          inputStreamtxt.read(bytestxt);
                          inputStreamtxt.close();
                          attVo.setAttbytes( bytestxt);  //图片附件
                          attVo.setTxtBytes(bytes);//txt附件
                          attVo.setAttName(txt.getOriginalFilename());
                          attVo.setTxtName( multipartFile.getOriginalFilename());
                          attVo.setAtttype(txt.getContentType());
                          attVo.setTxttype( multipartFile.getContentType());
                          attVos.add(attVo);
                      }
                      mm7VO.setAttVoList(attVos);

                      //mm7VO.setAttName(MD5Code.getInstance().getCode(String.valueOf(System.currentTimeMillis()))+multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")));

                  }
              }
              MMSfactory mmSfactory=new MMSfactory(mm7VO);
              mmSfactory.send();
          }
        return res;
    }

    /**第三方接口*/
    @RequestMapping(value = "/third/thirdPartImpl")
    @ResponseBody
    public Object thirdPartImpl(HttpSession session,ThirdSmsBody thirdSmsBody,
                                HttpServletRequest request, HttpServletResponse response){
        //setSession(request);

        /*if(session==null)return "请到登录页面登录！";
        SessionVo sessionVo =(SessionVo) session.getAttribute(SESSION_KEY);
        if(sessionVo.getId()==null || sessionVo.getId()==0)return "请到登录页面登录！";*/
        if(thirdSmsBody==null||"".equals(thirdSmsBody.getPhoneNo()))return "err";
        LoginVo loginVo=new LoginVo();
        loginVo.setUserId(thirdSmsBody.getUserId().toString());
        loginVo.setPassWord(thirdSmsBody.getPassWord());
        SessionVo sessionVo = dataAccessService.getLoginVo(loginVo);
        if(sessionVo==null||"".equals(thirdSmsBody.getPhoneNo())||thirdSmsBody.getPhoneNo()==null)return "erruserorphone";

         String[] phones = StringUtils.split(thirdSmsBody.getPhoneNo(),",");
        if(phones==null||phones.length==0||phones.length>100)return "errphone";
        List<SmsBody> slist=new ArrayList<SmsBody>();
        for(String linText:phones){
            if(linText!=null&&!"".equals(linText)&&linText.length()>=11){
                linText=linText.trim();
                if(linText.startsWith("1")&&linText.length()==11){
                    SmsBody s=new SmsBody();
                    s.setServiceId("106289975");
                    s.setUserId(sessionVo.getId());
                    s.setPhoneNo("86"+linText);
                    s.setMsg(thirdSmsBody.getMsg());
                    s.setReserve("000000");
                    slist.add(s) ;
                }else if(linText.startsWith("8")&&linText.length()==13){
                    SmsBody s=new SmsBody();
                    s.setServiceId("106289975");
                    s.setUserId(sessionVo.getId());
                    s.setPhoneNo(linText);
                    s.setMsg(thirdSmsBody.getMsg());
                    s.setReserve("000000");
                    slist.add(s) ;
                }
            }
        }
        int ii= dataAccessService.batchSendSMS(slist);

        return ii;
    }





    /*测试用,设置session*/
    public void setSession(HttpServletRequest request){
        HttpSession session =  request.getSession();
        SessionVo sessionVo =new SessionVo() ;
        sessionVo.setId(88L);
        session.setAttribute(SESSION_KEY,sessionVo);
    }

}
