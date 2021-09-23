package com.company;

/**
 * 分析子弹除公共特征以外的属性
 * 速度
 */
public class Bullet extends FlyingObject{
    private int speed=3;
        /*
        初始小蜜蜂
        1、初始化子弹的图片
        2、获得子弹的图片的宽
        3、获得子弹的图片的高
        4、子弹初始位置
     */
    public Bullet(int x,int y){
        image =ShootGame.bullet;//初始化子弹的图片
        width=image.getWidth();//获得子弹的图片的宽
        height= image.getHeight();//获得子弹的图片的高
        this.x=x;//子弹初始x位置
        this.y=y;//子弹初始y位置
    }
    /*
    重写走步方法
     */
    @Override
    public void step() {
        y-=speed;
    }
    public boolean outOfBounds(){
        return y<-height;
    }
}
