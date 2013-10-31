package com.VO;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class ComparatorBreath implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        BreathVO temperatureVO1=(BreathVO)o1;
        BreathVO temperatureVO2=(BreathVO)o2;
        if(temperatureVO1.getPoint()>temperatureVO2.getPoint()){
            return 1;
        }else if(temperatureVO1.getPoint()==temperatureVO2.getPoint()){
            if(temperatureVO1.getP2()>temperatureVO2.getP2()){
                return 1;
            } else if(temperatureVO1.getP2()==temperatureVO2.getP2()){
                return 0;
            }else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}
