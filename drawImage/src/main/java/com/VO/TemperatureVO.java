package com.VO;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class TemperatureVO {
    private float temperature;
    private Date date;
    private int point;
    /**1口温 2,腋温 3,肛温*/
    private String temperatureType;
    private int x;
    private int y;

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

    public String getTemperatureType() {
        return temperatureType;
    }

    public void setTemperatureType(String temperatureType) {
        this.temperatureType = temperatureType;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
