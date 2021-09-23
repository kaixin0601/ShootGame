package com.company;

import java.util.Random;

/**
 * 分析小蜜蜂除公共特征以外的属性
 * 左右移动速度
 * 分析小蜜蜂的奖励:生命和双倍火力
 *
 */
public class Bee extends FlyingObject implements Award{
    private int xspeed=1;
    private int yspeed=2;
    private int awardType;
        /*
            初始小蜜蜂
            1、初始化小蜜蜂的图片
            2、获得小蜜蜂的图片的宽
            3、获得小蜜蜂的图片的高
            4、小蜜蜂初始位置
            5、小蜜蜂奖励类型
         */
    public Bee(){
        image=ShootGame.bee;//小蜜蜂的初始图片
        width=image.getWidth();//获得小蜜蜂的图片的宽
        height=image.getHeight();//获得小蜜蜂的图片的高
/*        x=100;//小蜜蜂初始固定x位值
        y=200;//小蜜蜂初始固定y位置*/
        y=-height;//小蜜蜂的y位置
        Random ran=new Random();
        x=ran.nextInt(ShootGame.WIDTH-width);//小蜜蜂随机x位置
        awardType=ran.nextInt(2);//小蜜蜂随机奖励
    }

    /*
     *重写走步方法
     */
    @Override
    public void step() {
        x+=xspeed;
        y+=yspeed;
        if(x>ShootGame.WIDTH-width){
            xspeed=-1;
        }
        if(x<0){
            xspeed=1;
        }

    }
    public boolean outOfBounds(){
        return y>ShootGame.HEIGHT;
    }
    /*
    实现小蜜蜂奖励接口
     */
    public int getType(){
        return awardType;
    }
}
