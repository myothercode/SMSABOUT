package com.VO;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class ComparatorPulse implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        PulseVO temperatureVO1=(PulseVO)o1;
        PulseVO temperatureVO2=(PulseVO)o2;
        if(temperatureVO1.getPoint()>temperatureVO2.getPoint()){
            return 1;
        }else if(temperatureVO1.getPoint()==temperatureVO2.getPoint()){
            return 0;
        } else {
            return -1;
        }
    }
}
