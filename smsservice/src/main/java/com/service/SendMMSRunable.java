package com.service;

import com.common.utils.MMSfactory;
import com.domainVO.AttVo;
import com.domainVO.MM7TableVO;
import com.domainVO.MM7VO;
import com.domainVO.QuenueVo;
import com.service.impl.GetMMSFromDBImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wula
 * Date: 13-11-24
 * Time: 下午5:14
 * To change this template use File | Settings | File Templates.
 */
public class SendMMSRunable implements Runnable {

    private GetMMSFromDB getMMSFromDB;

    String name="";
    //@Override
    public void run() {
        Thread.currentThread().setName(this.name);
        while (true){
            try {
                MM7TableVO mm7TableVO= QuenueVo.smsQueue.take();

                List<AttVo> attVos =  getAtts(mm7TableVO);

                MM7VO mm7VO=new MM7VO();
                mm7VO.setPhone(mm7TableVO.getPhoneNo());
                mm7VO.setTitle("-");
                mm7VO.setYewuCode(mm7TableVO.getServiceCode());
                mm7VO.setYewuNum(mm7TableVO.getServiceId());
                mm7VO.setAttVoList(attVos);

                MMSfactory mmSfactory=new MMSfactory(mm7VO);
                mmSfactory.send();

                if (getMMSFromDB==null){
                    WebApplicationContext webApplication = ContextLoader.getCurrentWebApplicationContext();
                    getMMSFromDB=(GetMMSFromDBImpl)webApplication.getBean("getMMSFromDB");
                }
                 getMMSFromDB.updateFlag(mm7TableVO.getIdnum());
                Thread.sleep(1000L);    //暂停一秒继续
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /** 获取附件*/
    private List<AttVo> getAtts(MM7TableVO mm7TableVO) throws Exception{
        Map[] ms=filterImage(mm7TableVO.getFirstPath(),mm7TableVO.getPaths());
        Map txtm=ms[0];
        Map imgm=ms[1];

        List<AttVo> attVos=new ArrayList<AttVo>();

        Set keys=txtm.keySet();
        Iterator iterator=keys.iterator();
        while (iterator.hasNext()){
            AttVo attVo=new AttVo();
            String name= (String) iterator.next();
            File txt=new File((String) txtm.get(name) );
            if (!txt.exists()){continue;}
            InputStream intxt=new FileInputStream(txt);
            byte [] bytestxt = new byte[intxt.available()];
            intxt.read(bytestxt);
            intxt.close();
            attVo.setTxtBytes(bytestxt);
            attVo.setTxttype("text/plain");
            attVo.setTxtName(name+".txt");

            File img=new File((String)imgm.get(name)) ;
            if (!img.exists()) continue;
            InputStream inimg=new FileInputStream(img);
            byte [] byteimg=new byte[inimg.available()];
            inimg.read(byteimg);
            inimg.close();
            attVo.setAttbytes(byteimg);
            attVo.setAtttype("image/jpeg");
            attVo.setAttName(name+".jpg");


            attVos.add(attVo);
        }
        return attVos;
    }

    /*将图片和txt过滤成map*/
    private Map[] filterImage(String firstPath,String paths) throws Exception{
        Map maptxt=new HashMap();
        Map mapimg=new HashMap();
       String[] ss= StringUtils.split(paths,",");
        for (String s : ss){
            if (s.endsWith(".txt")){
                maptxt.put(s.substring(0,s.lastIndexOf(".")),firstPath+s);
            }
            if (s.endsWith(".jpg")||s.endsWith(".gif")) {
                mapimg.put(s.substring(0,s.lastIndexOf(".")),firstPath+s) ;
            }

        }
        return new Map[]{maptxt,mapimg};
    }

    public SendMMSRunable(String name){
        this.name=name;
    }
}
