package com.company;

import java.util.Random;

/**
 * 分析敌机除公共特征以外的属性
 * 速度
 */
public class Airplane extends FlyingObject implements Emeny{
    public int speed=2;
        /*
    初始小蜜蜂
    1、初始化敌机的图片
    2、获得敌机的图片的宽
    3、获得敌机的图片的高
    4、敌机初始位置
     */
    public Airplane(){
        image=ShootGame.airplane;//初始化敌机的图片
        width= image.getWidth();//获得敌机的图片的宽
        height=image.getHeight();//获得敌机的图片的高
/*        x=100;
        y=100;*/

        y=-height;//敌机的y位置
        Random ran=new Random();
        x=ran.nextInt(ShootGame.WIDTH-width);//敌机随机x位置

    }
    /*
    重写走步方法
     */
    @Override
    public void step() {
        y+=speed;
    }
    /*获取分数*/
    public int getScore(){
        return 5;
    }
    public boolean outOfBounds(){
        return y>ShootGame.HEIGHT;
    }
}
