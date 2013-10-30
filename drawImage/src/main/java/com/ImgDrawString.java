package com;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;

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
        graphics.drawString("时间",40,167);
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

    }

    /*字体红色*/
    private void drawStringRed(String st,int x,int y){
        graphics.setColor(Color.RED);
        graphics.drawString(st,x,y);
        graphics.setColor(Color.BLACK);
    }

}
