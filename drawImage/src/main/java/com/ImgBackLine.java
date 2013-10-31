package com;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Chace.Cai
 * Date: 13-10-30
 * Time: 上午9:05
 * To change this template use File | Settings | File Templates.
 */
/**画背景线*/
public class ImgBackLine extends ImgMain {
    /**画出XY*/
    public void drawLine() throws IOException {
        init();
        Line2D lineY=new Line2D.Double(92,155,92,785);//Y轴
        Line2D lineTitle=new Line2D.Double(0,170,750,170);//脉搏体温标题线
        Line2D lineTime=new Line2D.Double(0,155,750,155);//时间线

        Line2D lineX=new Line2D.Double(0,185,750,185); //顶层X轴
        drawXlineB(lineX,Color.BLACK);
        Line2D lineX2=new Line2D.Double(0,785,750,785); //地步X轴
        drawXlineB(lineX2,Color.BLACK);

        graphics.draw(lineY);
        graphics.draw(lineTitle);
        graphics.draw(lineTime);
        drawXline();
        drawYline();

        drawTitleTable();
        breathLine();
        bloodPressureLine();
        drawOtherLine();
    }

    /**画出其它入量出量等线条*/
    private void drawOtherLine(){
        double xx1=0,yx1=845,xx2=720,yx2=845; //横线
        double xy1=92,yy1=845,xy2=92,yy2=920; //竖线
        Line2D lineX;
        for (int i=0;i<=4;i++){
            yx1+=15;
            yx2+=15;
            lineX=new Line2D.Double(xx1,yx1,xx2,yx2);
            graphics.draw(lineX);
        }

        Line2D lineY;
        for (int i=0;i<8;i++){
            lineY =new Line2D.Double(xy1,yy1,xy2,yy2) ;
            graphics.draw(lineY);
            xy1+=90;
            xy2+=90;
        }
    }

    /**血压线*/
    private void bloodPressureLine(){
        Line2D lineX=new Line2D.Double(0,845,720,845);
        graphics.draw(lineX);

        Line2D linY;
        double xy1=92,yy1=830,xy2=92,yy2=845; //竖线
        for(int i=0;i<=14;i++){
            linY=new Line2D.Double(xy1,yy1,xy2,yy2);
            graphics.draw(linY);
            xy1+=45;
            xy2+=45;
        }

    }

    /**呼吸频率线*/
    private void  breathLine(){
        Line2D lineX=new Line2D.Double(0,830,720,830);
        graphics.draw(lineX);

        Line2D linY;
        double xy1=92,yy1=785,xy2=92,yy2=830; //竖线
        for (int i=0;i<=42;i++){
            linY=new Line2D.Double(xy1,yy1,xy2,yy2);
            graphics.draw(linY);
            xy1+=15;
            xy2+=15;
        }

    }

    /*画出前三行标题行*/
    private void drawTitleTable(){
        double xx1=0,yx1=155,xx2=720,yx2=155; //横线
        double xy1=92,yy1=110,xy2=92,yy2=155; //竖线
        Line2D lineX;
        for (int i=1;i<=3;i++) {
            yx1-=15;
            yx2-=15;
            lineX =new Line2D.Double(xx1,yx1,xx2,yx2) ;
            graphics.draw(lineX);
        }
        Line2D lineY;
        for (int i=0;i<8;i++){
        lineY =new Line2D.Double(xy1,yy1,xy2,yy2) ;
        graphics.draw(lineY);
        xy1+=90;
        xy2+=90;
        }
    }

    /*竖向画线*/
    public void drawYline(){
        double xjg=15;
        double xinit=92;
        double xinit2=92;
        double yinit=155;
        double yinit2=785;
        Line2D lineys;

        for (int i=0;i<42;i++){
            xinit += xjg;
            xinit2 += xjg;
            lineys=new Line2D.Double(xinit,yinit,xinit2,yinit2);
            if((i==5||(i-5)%6==0)&&i!=41){
                drawYlineRed(lineys);
            }else {
                graphics.draw(lineys);
            }


        }
    }

    /*竖向画红线*/
    public void drawYlineRed(Line2D line){
        graphics.setColor(Color.RED);
        graphics.draw(line);
        graphics.setColor(Color.BLACK);
    }

    /*横向画线*/
    public void drawXline(){
        double yjg=15;
        double yinit=185;
        double yinit2=185;
        double xinit=92;
        Line2D linexs;
        for(int i=0;i<40;i++){
            yinit += yjg;
            yinit2 += yjg;
            linexs =new Line2D.Double(xinit,yinit,720,yinit2);
            if(i==4||((i-4)%5==0)){
                //if(i==2||i==(2+5)||i==(2+10)||i==(2+15)||i==(2+20)||i==(2+25)||i==(2+30)||i==(2+35)){
                if(i==24){
                    drawXlineB(linexs,Color.RED);
                } else {
                    drawXlineB(linexs,Color.BLACK);
                }

            }else {
                graphics.draw(linexs);
            }

        }

    }

    /*画横向粗线*/
    public void drawXlineB(Line2D line,Color color){
        Stroke stroke=graphics.getStroke();
        graphics.setStroke(new BasicStroke(2f,BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        graphics.setColor(color);
        graphics.draw(line);
        graphics.setStroke(stroke); //将画刷复原
        graphics.setColor(Color.BLACK);
    }
}
