package com.utils;

import com.VO.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    /**joda计算指定日期指定周后的日期周一日期*/
    public static Date getDateAfterWeek(Date date,int afterWeek){
        DateTime dateTime=new DateTime(date);
        DateTime then=dateTime.plusWeeks(afterWeek).dayOfWeek().withMinimumValue();
        return then.toDate();
    }

    /**获取指定日期所在周的七天日期map*/
    public static Map<Integer,Date> getDasInWeek(Date date){
        Date d=getFirstDayOfWeek(date);
        DateTime dateTime=new DateTime(d);
        Map<Integer,Date> map=new HashMap<Integer,Date>();
        for (int i=0;i<7;i++){
           Date dw = dateTime.plusDays(i).toDate();
            map.put(i+1,dw);
        }
        return map;
    }

    /**计算两个日期之间相差几周*/
    public static int getTwoDateWeekNum(Date date1,Date date2){
        int i= Weeks.weeksBetween(new DateTime(date1),new DateTime(date2)).getWeeks();
        return i;
        /*Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime (date1);
        int before = c.get(Calendar.WEEK_OF_YEAR);  //得到指定日期所在年是第几周
        c.setTime(date2);
        int after = c.get(Calendar.WEEK_OF_YEAR);
        return after-before==0?1:(after-before);*/
    }

    /**得到指定日期在所在周是第几天*/
    public static int getNumDayInWeek(Date date){
          DateTime dateTime=new DateTime(date);
        return dateTime.dayOfWeek().get();
    }

    /**
     * 计算指定日期所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        DateTime dateTime=new DateTime(date);
        return dateTime.dayOfWeek().withMinimumValue().toDate();
        /*Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime ();*/
    }

    /**
     * 得到指定日期所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date){
        DateTime dateTime=new DateTime(date);
        return dateTime.dayOfWeek().withMaximumValue().toDate();
    }

    /** 时间转为当天 00:00:00 */
    public static Date turnToDateStart(Date date) {
        DateTime dateTime=new DateTime(date);
        return dateTime.withTimeAtStartOfDay().toDate();

        /*Calendar calendar = getCalendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();*/
    }
    /**日期转为当天23:00:00*/
    public static Date turnToDateEnd(Date date){
        DateTime dateTime=new DateTime(date);
        return  dateTime.withTime(23,59,59,0).toDate();
    }

    /**比较两个日期的大小，返回结果为第一个日期比第二个日期大或者小.  -1 0 1*/
    public static int comparTwoDate(Date date1,Date date2){
        DateTime dateTime1=new DateTime(turnToDateStart(date1));
        DateTime dateTime2=new DateTime(turnToDateStart(date2));
        if(dateTime1.isAfter(dateTime2)){
            return 1;
        } else if (dateTime1.isBefore(dateTime2)){
            return -1;
        }else {
            /*判断两个日期相等*/
            //dateTime1.isEqual(dateTime2);
            return 0;
        }
    }

    /** 时间转指定格式的字符串 yyyy-MM-dd HH:mm:ss*/
    public static String date2String(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern, Locale.CHINA);
    }
    public static String date2StringFull(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    }
    public static String date2StringYMD(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd", Locale.CHINA);
    }

    /**
     *字符串转时间
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String dateStr) throws ParseException {
        return dateTimeFormatThreadLocal.get().parse(dateStr);
    }

    private static final ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance(Locale.CHINA);
        }
    };
    private static final ThreadLocal<DateFormat> dateTimeFormatThreadLocal = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private static Calendar getCalendar(Date date) {
        Calendar calendar = calendarThreadLocal.get();
        calendar.setTime(date);
        return calendar;
    }

    /**判断一个List<int[]>是否包含某个int[] */
    public static boolean intsIsInListints(List<int[]> list,int[] ints){
       Iterator iterator=list.iterator();
        boolean res=false;
        while (iterator.hasNext()){
            int[] ints1=(int[])iterator.next();
           if(ints.length != ints1.length){continue;};
           if(compar2Arr(ints1,ints)){
               res=true;
               break;
           }else {continue;}
        }
        return res;
    }
    private static boolean compar2Arr(int[] i1,int[] i2){
         boolean m=true;
        for (int i=0;i<i1.length;i++){
            if(i1[i]!=i2[i]){
                m=false;
                break;
            }
        }
        return m;
    }

    /**将时间转为对应的时间点*/
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
    /**提取日期里面的小时数字，用于当时间点相同的时候进行排序*/
    public static int getDateHour(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int h= calendar.get(Calendar.HOUR_OF_DAY);
        return h;
    }

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
        Object[] o=needBreakpoint(new Object[]{xs,ys});
        return o;
    }
    /**根据坐标集合，判断是否有需要进行断点处理的，重新组装后返回list<int[]>*/
    private static Object[] needBreakpoint(Object[] obs){
        int[] xs=(int[])obs[0];
        int[] ys=(int[])obs[1];
        List<int[]> lix=new ArrayList<int[]>();
        List<int[]> liy=new ArrayList<int[]>();
        int[] xs1=new int[7];
        int[] ys1=new int[7];
        xs1[0]=xs[0];
        ys1[0]=ys[0];
        //lix.add(xs1);
        //liy.add(ys1);
        for(int i=1;i<xs.length;i++){
            if(xs[i-1]!=xs[i]-15){
                lix.add(xs1);
                liy.add(ys1);
                xs1=new int[7];
                ys1=new int[7];
                xs1[i]=xs[i];
                ys1[i]=ys[i];
                continue;
            } else {
                xs1[i]=xs[i];
                ys1[i]=ys[i];
                continue;
            }

        }
        lix.add(xs1);
        liy.add(ys1);
        Object[] o=delRepeatArr(new Object[]{lix,liy}) ;
        return o;
    }
    private static Object[] delRepeatArr(Object[] obs){
        List<int[]> xs=( List<int[]>)obs[0];
        List<int[]> ys=( List<int[]>)obs[1];
        List<int[]> xs1= new ArrayList<int[]>();
        List<int[]> ys1= new ArrayList<int[]>();
       for (int i=0;i<xs.size();i++){
          // List<Integer> lx=arr2List((int[])xs.get(i));
           List<Integer> tempX=new ArrayList<Integer>();
           List<Integer> tempY=new ArrayList<Integer>();
           for(int ii=0;ii<xs.get(i).length;ii++){
               int xi=(xs.get(i))[ii];
               if(xi!=0){
                   tempX.add(xi);
                   tempY.add(ys.get(i)[ii]);
               }
           }
           xs1.add(list2Arr(tempX));
           ys1.add(list2Arr(tempY));
       }
       return new Object[]{xs1,ys1};

    }
    /*将list转数组，为防止基本类型转换陷阱，进行深度转换*/
    private static int[] list2Arr(List lint){
        int[] ints=new int[lint.size()];
        for (int i=0;i<lint.size();i++){
            ints[i]=(Integer)lint.get(i);
        }
        return ints;
    }
    /*将数组转为list，为防止基本类型转换陷阱，进行深度转换*/
    private List<Integer> arr2List(int[] ints){
         List<Integer> l=new ArrayList();
        for (int i:ints){
            l.add(i);
        }
        return l;
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

    /**对呼吸集合进行排序和索引标记(一天之内一个时间段一次)*/
    public static void sortListByTimePoint(List<BreathVO> breathVOList){
        ComparatorBreath comparatorBreath=new ComparatorBreath();
        Collections.sort(breathVOList,comparatorBreath);
        for (int i=0;i<breathVOList.size();i++){
            BreathVO breathVO= breathVOList.get(i);
            if(breathVO.getPoint()==1){breathVO.setIndex(2);}else {
                if (breathVO.getPoint()%2==0){breathVO.setIndex(3);}else {
                    breathVO.setIndex(2);
                }
            }
        }
    }

    /**对呼吸集合，时间点相同的元素进行索引标记(一天之内，一个时间点多次)*/
    public static List<BreathVO> sortListByTimePoint1(List<BreathVO> breathVOList){
        Map map=new HashMap();
        List<BreathVO> breathVOList1=new ArrayList<BreathVO>();
        /*先进行遍历，找出有哪些时间点，并累计时间点的个数*/
        for (BreathVO breathVO : breathVOList){
           if(map.containsKey(breathVO.getPoint())){
               List<BreathVO> breathVOs= (List<BreathVO>) map.get(breathVO.getPoint());
               breathVOs.add(breathVO);
           }else {
               List<BreathVO> breathVOs=new ArrayList<BreathVO>();
               breathVOs.add(breathVO);
               map.put(breathVO.getPoint(),breathVOs);
           }
        }
        /*遍历map，并对mmap集合进行排序*/
        Set key=map.keySet();
        for (Iterator iterator=key.iterator();iterator.hasNext();){
            List<BreathVO> breathVOs = (List<BreathVO>) map.get(iterator.next());
            ComparatorBreath comparatorBreath=new ComparatorBreath();
            Collections.sort(breathVOs,comparatorBreath);
            for (int i=0;i<breathVOs.size();i++){
                BreathVO breathVO= breathVOs.get(i);
                breathVO.setIndex(i+1);
            }
            breathVOList1.addAll(breathVOs);
        }
        return breathVOList1;
    }


}
