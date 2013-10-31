package com;

import com.VO.BreathVO;
import com.VO.CommonStringVO;
import com.VO.PulseVO;
import com.VO.TemperatureVO;
import com.utils.CommonUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-29
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class ImgMain {
    public BufferedImage image;
    public Graphics2D graphics;
    /**初始化*/
    public void init(){
        int width=750,height=1000;
        image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        graphics=(Graphics2D)image.getGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.clearRect(0,0,width,height);
        graphics.setColor(Color.BLACK);

        graphics.setStroke(new BasicStroke(0.5f,BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
    }


    /**输出图片*/
    public void outImage(String type,String filePath) throws IOException {
        graphics.dispose();
        ImageIO.write(image, type, new File(filePath));
        image.flush();
        //outImage("png","d:/test.png");
    }


    public static void main(String[] args) throws Exception {
        ImgPoint imgBackLine=new ImgPoint();
        imgBackLine.drawLine();//画出背景线
        imgBackLine.writeScaleValue();//画出刻度值



        java.util.List<TemperatureVO> list=new ArrayList<TemperatureVO>();
        TemperatureVO temperatureVO=new TemperatureVO();
        temperatureVO.setDate(CommonUtil.str2Date("2013-10-30 13:25:25"));
        temperatureVO.setTemperature(35.5f);
        list.add(temperatureVO);

        TemperatureVO temperatureVO2=new TemperatureVO();
        temperatureVO2.setDate(CommonUtil.str2Date("2013-10-30 22:25:25"));
        temperatureVO2.setTemperature(36.5f);
        list.add(temperatureVO2);

        TemperatureVO temperatureVO3=new TemperatureVO();
        temperatureVO3.setDate(CommonUtil.str2Date("2013-10-30 18:25:25"));
        temperatureVO3.setTemperature(37.5f);
        list.add(temperatureVO3);

        TemperatureVO temperatureVO4=new TemperatureVO();
        temperatureVO4.setDate(CommonUtil.str2Date("2013-10-30 8:25:25"));
        temperatureVO4.setTemperature(39.5f);
        list.add(temperatureVO4);

         //=======================================

        java.util.List<PulseVO> list1=new ArrayList<PulseVO>();
        PulseVO PulseVO=new PulseVO();
        PulseVO.setDate(CommonUtil.str2Date("2013-10-30 13:25:25"));
        PulseVO.setPulse(120);
        list1.add(PulseVO);

        PulseVO PulseVO1=new PulseVO();
        PulseVO1.setDate(CommonUtil.str2Date("2013-10-30 8:25:25"));
        PulseVO1.setPulse(130);
        list1.add(PulseVO1);

        PulseVO PulseVO2=new PulseVO();
        PulseVO2.setDate(CommonUtil.str2Date("2013-10-30 17:25:25"));
        PulseVO2.setPulse(160);
        list1.add(PulseVO2);

        PulseVO PulseVO3=new PulseVO();
        PulseVO3.setDate(CommonUtil.str2Date("2013-10-30 21:25:25"));
        PulseVO3.setPulse(50);
        list1.add(PulseVO3);

        //==呼吸频率
        BreathVO breathVO=new BreathVO();
        breathVO.setBreath(80);
        breathVO.setDate(CommonUtil.str2Date("2013-10-30 11:25:25"));
        BreathVO breathVO1=new BreathVO();
        breathVO1.setBreath(90);
        breathVO1.setDate(CommonUtil.str2Date("2013-10-30 6:25:25"));
        BreathVO breathVO2=new BreathVO();
        breathVO2.setBreath(20);
        breathVO2.setDate(CommonUtil.str2Date("2013-10-30 2:25:25"));

        java.util.List<BreathVO> list2=new ArrayList<BreathVO>();
        list2.add(breathVO);
        list2.add(breathVO1);
        list2.add(breathVO2);

        java.util.List<CommonStringVO> list5=new ArrayList<CommonStringVO>();
        CommonStringVO commonStringVO=new CommonStringVO();
        commonStringVO.setValString("20/52");
        commonStringVO.setDate(CommonUtil.str2Date("2013-10-30 2:25:25"));
        CommonStringVO commonStringVO1=new CommonStringVO();
        commonStringVO1.setValString("45/52");
        commonStringVO1.setDate(CommonUtil.str2Date("2013-10-30 20:25:25"));
        list5.add(commonStringVO1);
        list5.add(commonStringVO) ;

        java.util.List<CommonStringVO> list6=new ArrayList<CommonStringVO>();
        CommonStringVO commonStringVO6=new CommonStringVO();
        commonStringVO6.setValString("kkkkkk");
        commonStringVO6.setDate(CommonUtil.str2Date("2013-10-30 20:25:25"));
        list6.add(commonStringVO6);

        imgBackLine.writeOther(3,list6,"tz");
        imgBackLine.writeBloodPressure(5,list5);
        imgBackLine.writeBreathString(3,list2);
        imgBackLine.drawTWPoint(2,list);  //画体温刻度，参数为周几，以及当天的检查情况
        imgBackLine.drawMBPoint(3,list1);



    }
}
