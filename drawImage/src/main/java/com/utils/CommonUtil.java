package com.utils;

import com.VO.ComparatorPulse;
import com.VO.ComparatorTemperature;
import com.VO.PulseVO;
import com.VO.TemperatureVO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {
    /*将时间转为对应的时间点*/
    public static int time2point(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int h= calendar.get(Calendar.HOUR_OF_DAY);
        int p=0;
       if(h<6&&h>=0){
         p=1;
       }else if(h>=6&&h<10){
           p=2;
       }else if(h>=10&&h<14){
           p=3;
       }else if (h>=14&&h<18){
           p=4;
       }else if (h>=18&&h<22){
           p=5;
       }else if (h>=22){
           p=6;
       }

        return p;
    }



    /*字符串转时间*/
    public static Date str2Date(String dateStr) throws ParseException {
        return dateTimeFormatThreadLocal.get().parse(dateStr);
    }

    private static final ThreadLocal<DateFormat> dateTimeFormatThreadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    /*将体温集合提取出xy坐标集合*/
    public static Object[] cover2Arr(List list){
        int[] xs=new int[list.size()];
        int[] ys=new int[list.size()];
        /*对集合排序*/
        ComparatorTemperature comparatorTemperature=new ComparatorTemperature();
        Collections.sort(list, comparatorTemperature);
        for(int i=0;i<list.size();i++){
            TemperatureVO temperatureVO=(TemperatureVO)list.get(i);
            xs[i]=temperatureVO.getX()+7;
            ys[i]=temperatureVO.getY()-7;
        }
        Object[] o=new Object[]{xs,ys};
        return o;
    }

    /*将脉搏集合提取出xy坐标集合*/
    public static Object[] cover2ArrMB(List list){
        int[] xs=new int[list.size()];
        int[] ys=new int[list.size()];
        /*对集合排序*/
        ComparatorPulse comparatorTemperature=new ComparatorPulse();
        Collections.sort(list, comparatorTemperature);
        for(int i=0;i<list.size();i++){
            PulseVO temperatureVO=(PulseVO)list.get(i);
            xs[i]=temperatureVO.getX()+7;
            ys[i]=temperatureVO.getY()-7;
        }
        Object[] o=new Object[]{xs,ys};
        return o;
    }

}
