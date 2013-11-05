package com;

import com.VO.ComparatorTemperature;
import com.VO.PulseVO;
import com.VO.TemperatureVO;
import com.sun.org.apache.xpath.internal.axes.WalkingIteratorSorted;
import com.utils.CommonUtil;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 上午10:28
 * To change this template use File | Settings | File Templates.
 */
public class ImgPoint extends ImgDrawString {
    private ThreadLocal<java.util.List> iList;

    /*脉搏线*/
    public void drawMBPoint(int week,java.util.List<PulseVO> tempList) throws IOException {
        if(tempList.isEmpty())return;
        List list=iList.get();
        /*15像素表示4次
        * 例如180次 (180-165)/4=?格*/
        int dayx=93+(6*15)*(week-1);
        for(PulseVO temperatureVO:tempList){
            temperatureVO.setPoint(CommonUtil.time2point(temperatureVO.getDate()));
            int[] xy= getCoordinateMB(dayx, temperatureVO.getPoint(), temperatureVO.getPulse());
            temperatureVO.setX(xy[0]);
            temperatureVO.setY(xy[1]);
            if(CommonUtil.intsIsInListints(list,xy)){//判断有没有重叠的
                drawEllipse(Color.RED, xy[0]+2, xy[1]-13, 10, 10);
            }else {
            drawMarkString(Color.RED,xy[0],xy[1],"●");
            }
        }
        Object[] objects=CommonUtil.cover2ArrMB(tempList);
        drawPolyLine((int[])objects[0],(int[])objects[1],tempList.size(),Color.RED);
        iList.remove();
     }

    /*体温点线*/
    public void drawTWPoint(int week,java.util.List<TemperatureVO> tempList) throws IOException {
         if(tempList.isEmpty())return;
        String yew="2";      //腋温 ×
        String kw="1";       //口温●
        String gw="3";       //肛温○

        iList=new ThreadLocal<java.util.List>()    ;
        java.util.List list=new ArrayList();
        /*
        *第一个刻度所在的坐标为93,187
         * 15像素表示0.2度
          * 例如40度  (42-40)/0.2=10格   ，每格15px，相隔150px
          *
         根据星期几，计算出当天的起始坐标*/
        int dayx=93+(6*15)*(week-1);

        for(TemperatureVO temperatureVO:tempList){
            temperatureVO.setPoint(CommonUtil.time2point(temperatureVO.getDate()));
            int[] xy= getCoordinate(dayx,temperatureVO.getPoint(),temperatureVO.getTemperature());
             list.add(xy) ;
            //drawEllipse(Color.RED,xy[0],xy[1],10,10);
            temperatureVO.setX(xy[0]);
            temperatureVO.setY(xy[1]);
            if(yew.equals(temperatureVO.getTemperatureType())){
                drawMarkString(Color.BLUE,xy[0],xy[1],"×");
            }else if(kw.equals(temperatureVO.getTemperatureType())){
                drawMarkString(Color.BLUE,xy[0],xy[1],"●");
            }else if(gw.equals(temperatureVO.getTemperatureType())){
                drawMarkString(Color.BLUE,xy[0],xy[1],"○");
            }

        }
        iList.set(list);
        Object[] objects=CommonUtil.cover2Arr(tempList);
        List<int[]> intListX=(List<int[]>)objects[0];
        List<int[]> intListY=(List<int[]>)objects[1];
         for (int i=0;i<intListX.size();i++){
             int [] xxs=intListX.get(i);
             int [] yys=intListY.get(i);
             drawPolyLine(xxs,yys,xxs.length,Color.BLUE);
         }



        //int h= CommonUtil.time2point(new Date()); //获取时间点
        //int[] xy= getCoordinate(dayx,h,37.5f);
        //drawEllipse(Color.RED,xy[0],xy[1],10,10);

    }
    /*获得体温xy坐标*/
    private int[] getCoordinate(int initx,int timePoint,float temperature){
       int x=initx + (timePoint-1)*15;
        //先求出当前温度距离初始位置的像素
        Float pxf=(42f-temperature)/0.2f;
        int px=pxf.intValue()*15;
        int y=187+px;
        return new int[]{x,y};
    }
    /*获得脉搏xy坐标*/
    private int[] getCoordinateMB(int initx,int timePoint,int temperature){
        int x=initx + (timePoint-1)*15;
        //先求出当前温度距离初始位置的像素
        int pxf=(180-temperature)/4;
        int px=pxf*15;
        int y=187+px;
        return new int[]{x,y};

    }

    /*画折线*/
    private void drawPolyLine(int[] xs,int[] ys,int length,Color color ){
        graphics.setColor(color);
        graphics.drawPolyline(xs,ys,xs.length);
        graphics.setColor(Color.BLACK);
    }

    /*用符号的字符串来画*/
    private void drawMarkString(Color color,int x,int y,String mark){
        Font f= graphics.getFont();
        graphics.setColor(color);
        graphics.setFont(new Font("宋体", Font.BOLD, 16));
        graphics.drawString(mark,x,y);

        graphics.setColor(Color.BLACK);
        graphics.setFont(f);
    }

    /*画圆 不填充*/
    private void drawEllipse(Color color,int x,int y,int w,int h){
        graphics.setColor(color);
        Ellipse2D ellipse2D=new Ellipse2D.Double(x,y,w,h);
        graphics.draw(ellipse2D);
        //graphics.fill(ellipse2D);
        graphics.setColor(Color.BLACK);
    }
}
