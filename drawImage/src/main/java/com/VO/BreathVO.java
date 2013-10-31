package com.VO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class BreathVO {
    private int breath;
    private Date date;
    private int point;   //表示第几个时间点
    private int p2;        //在时间点相同的情况下，再次比较大小对其进行排序(时间hour)
    private int index;    //排序序号，只有1.2.3三个取值
    private int x;
    private int y;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getBreath() {
        return breath;
    }

    public void setBreath(int breath) {
        this.breath = breath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }
}
