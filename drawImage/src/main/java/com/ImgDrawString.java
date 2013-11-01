package com;

import com.VO.BreathVO;
import com.VO.CommonStringVO;
import com.VO.ComparatorBreath;
import com.utils.CommonUtil;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 上午9:21
 * To change this template use File | Settings | File Templates.
 *
 */
public class ImgDrawString extends ImgBackLine {
    /**画出刻度值*/
    public void writeScaleValue() throws IOException {
        graphics.drawString("体温",60,182);
        graphics.drawString("(℃)",60,200);
        graphics.drawString("脉搏",10,182);
        graphics.drawString("(次/分)",10,200);

        graphics.drawString("42",60,215);
        graphics.drawString("180",10,215);

        /*体温*/
        int inity=200;
        for(int i=41;i>=35;i--){
            inity+=75;
            graphics.drawString(String.valueOf(i),60,inity);
        }
        /*脉搏*/
        int inity2=200;
        for (int i=160;i>=40;i=i-20){
          inity2+=75;
          graphics.drawString(String.valueOf(i), 10, inity2);
        }
        /*=====时间刻度线==============================================================================*/
        graphics.drawString("时   间",5,167);
        String[] hours=new String[]{"2","6","10","14","18","22"};
          int x=93;
        for (int i=0;i<7;i++){
            for(int ii=0;ii<hours.length;ii++){
                if(ii==0||ii==4||ii==5){
                    drawStringRed(hours[ii],x,167);
                }else {
                    graphics.drawString(hours[ii],x,167);
                }
                x+=15;
            }
        }

        /*========手术后天数==========================================*/
        graphics.drawString("手术后天数",5,153);
        /*=========住院天数=============================================*/
        graphics.drawString("住院天数",5,138);
        /*===========时间==========================================*/
        graphics.drawString("日   期",5,122);
        /*===========呼吸==============================================*/
        graphics.drawString("呼吸(次/分)",5,815);
        /*===============血压=========================================*/
        graphics.drawString("血压(mmHg)",5,842);
        /*============其它数值==============================*/
        graphics.drawString("入量(ml)",5,857);
        graphics.drawString("出量(ml)",5,872);
        graphics.drawString("大便(次/日)",5,887);
        graphics.drawString("体重(kg)",5,902);
        graphics.drawString("身高(cm)",5,917);

    }

    /**其它数据*/
    public void writeOther(int week,java.util.List<CommonStringVO> commonStringVOList,String type){
         //rl入量   cl出量  db大便  tz体重   sg身高
        if (commonStringVOList.isEmpty())return;
        int dayx=93+(6*15)*(week-1);
        //int xrl = dayx ,xcl=dayx+90 , xdb=dayx+180,xtz=270,xsg=360;
        int yrl=857 , ycl=872 , ydb=887 , ytz=902 , ysg=917  ;
        Map map=new HashMap();
        map.put("rl",new int[]{dayx,yrl});
        map.put("cl",new int[]{dayx,ycl});
        map.put("db",new int[]{dayx,ydb});
        map.put("tz",new int[]{dayx,ytz});
        map.put("sg",new int[]{dayx,ysg});

        int[] i=(int[])map.get(type);
        String v= commonStringVOList.get(0).getValString();
        graphics.drawString(v,i[0],i[1]);
    }

    /**填写日期
     *
     * @param inHosDate  入院日期 r
     * @param weekNum    第几周 d
     */
    public void writeWeekDates(Date inHosDate,int weekNum){
            Date d=CommonUtil.getDateAfterWeek(inHosDate,weekNum-1);
            Map<Integer,Date> days=CommonUtil.getDasInWeek(d);
        int i1=1;
        for (int i=1;i<=7;i++){
            graphics.drawString(CommonUtil.date2StringYMD(days.get(i)),93+(6*15)*(i1-1),122);
            i1++;
        }
    }

    /**填写住院天数*/
    public void writeInHospDays(Date inHosDate,int weekNum){
        int firstWeekLastDayNum=0;//第一周最后一天是住院的第几天
         if(weekNum==1){
             int d=CommonUtil.getNumDayInWeek(inHosDate);//的搭配入院日期在本周是第几天
             firstWeekLastDayNum=7-d+1;
             for (int i=1;i<=firstWeekLastDayNum;i++){
                 int dayx=93+(6*15)*(d-1);
                 graphics.drawString(String.valueOf(i),dayx,138);
                 d++;
             }

         }else {
             int d=1;
             firstWeekLastDayNum=(7-CommonUtil.getNumDayInWeek(inHosDate)+1)+((weekNum-2)*7);
             for (int i=1;i<=7;i++){
                 int dayx=93+(6*15)*(d-1);
                 graphics.drawString(String.valueOf(i+firstWeekLastDayNum),dayx,138);
                 d++;
             }
             firstWeekLastDayNum+=7;
         }
    }

    /**填写血压(week是当周的第几天)*/
    public void writeBloodPressure(int week,java.util.List<CommonStringVO> bloodPressureVOList){
          if (bloodPressureVOList.isEmpty())return;
        int dayx=93+(6*15)*(week-1);
        for (CommonStringVO commonStringVO:bloodPressureVOList){
            int tp=CommonUtil.time2point(commonStringVO.getDate()) ;
            commonStringVO.setPoint(tp<=3?1:2);
            int[] xy=getCoordinateXY(dayx, commonStringVO.getPoint());
            commonStringVO.setX(xy[0]);
            commonStringVO.setY(xy[1]);
            graphics.drawString(commonStringVO.getValString(), xy[0],xy[1]);
        }
    }
    /**获得血压的xy坐标*/
    private int[] getCoordinateXY(int initx,int timePoint){
        int x=initx + (timePoint-1)*45;
        //先求出当前温度距离初始位置的像素
        int y=842;
        return new int[]{x,y};
    }

    /**填写呼吸频率*/
    public void writeBreathString(int week,java.util.List<BreathVO> breathVOList){
        if(breathVOList.isEmpty())return;
        int dayx=93+(6*15)*(week-1);
        /*先对各个元素进行标记时间点和时间小时单位*/
        for (BreathVO breathVO:breathVOList){
            breathVO.setPoint(CommonUtil.time2point(breathVO.getDate()));
            breathVO.setP2(CommonUtil.getDateHour(breathVO.getDate()));
        }
        /*对该集合相同时间点的元素进行索引标记，排序依据为时间先后*/
       CommonUtil.sortListByTimePoint(breathVOList);
        /*进行排序、设值处理后，进行画值*/
        for (BreathVO breathVO:breathVOList){
            int[] xy=getCoordinateHX(dayx,breathVO.getPoint(),breathVO.getIndex());
            breathVO.setX(xy[0]);
            breathVO.setY(xy[1]);
            graphics.drawString(String.valueOf(breathVO.getBreath()), xy[0],xy[1]);
        }

    }
    /*获得呼吸频率xy坐标*/
    private int[] getCoordinateHX(int initx,int timePoint,int index){
        int x=initx + (timePoint-1)*15;
        //先求出当前温度距离初始位置的像素
        int y=800+((index-1)*15);
        return new int[]{x,y};
    }

    /*字体红色*/
    private void drawStringRed(String st,int x,int y){
        graphics.setColor(Color.RED);
        graphics.drawString(st, x, y);
        graphics.setColor(Color.BLACK);
    }

}
