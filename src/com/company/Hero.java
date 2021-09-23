package com.company;

import java.awt.image.BufferedImage;

/**
 * 分析英雄机除公共特征以外的属性
 * 双倍火力、生命、双图片切换
 */
public class Hero extends FlyingObject{
    private BufferedImage [] images={};//定义一个空数组,用来存储英雄机的图片的
    private int double_fire;//定义双倍火力
    private int life;//定义生命
    private int index;//图片切换计数
    /*
    初始化英雄机
    1、英雄机的初始生命
    2、英雄机的初始火力值
    3、英雄机的初始图片
    4、获得英雄机图片的宽
    5、获得英雄机图片的高
    6、英雄机的初始固定位值
    7、英雄机两张图片切换
     */
    public  Hero(){
        life=3;//英雄机初始生命值为3条命
        double_fire=0;//英雄机的初始火力值为0
        image=ShootGame.hero0;//英雄机的初始图片为hero0
        width=image.getWidth();//获得英雄机图片的宽
        height=image.getHeight();//获得英雄机图片的高
        x=150;//英雄机x坐标位置
        y=400;//英雄机y坐标位置
        images=new BufferedImage [] {ShootGame.hero0,ShootGame.hero1 };//存放两张英雄机的图片，用于切换
    }

    /*
     *
     */
    @Override
    public void step() {
        index=0;
        if(images.length>0){
            image=images[index++/10%images.length];//step()方法被调用10次交换一次图片
        }
        /*
        index =0    0/10=0    0%2=0    image=images[0];
        index =1    1/10=0    0%2=0    image=images[0];
        index =2    2/10=0    0%2=0    image=images[0];
        index =3    3/10=0    0%2=0    image=images[0];
        index =4    4/10=0    0%2=0    image=images[0];
        index =5    5/10=0    0%2=0    image=images[0];
        index =6    6/10=0    0%2=0    image=images[0];
        index =7    7/10=0    0%2=0    image=images[0];
        index =8    8/10=0    0%2=0    image=images[0];
        index =9    9/10=0    0%2=0    image=images[0];
        index =10   10/10=0   1%2=0    image=images[1];
         */
    }

    /*
    shoot方法
    1、将英雄机图片的x分为4等分
    2、如果double_fire>0则是是双倍火力,反之是单倍火力
     */
    public Bullet [] shoot(){
        int xspeed=this.width/4;//将英雄机图片的x分为4等分
        int yspeed=20;
        if(double_fire>0){
            Bullet [] bullets=new Bullet[2];
            bullets[0]=new Bullet(this.x+xspeed,this.y-yspeed);
            bullets[1]=new Bullet(this.x+3*xspeed,this.y-yspeed);
            return bullets;
        }else{
            Bullet [] bullets=new Bullet[1];
            bullets[0]=new Bullet(this.x+2*xspeed,this.y-yspeed);
            return bullets;
        }
    }
    //创建无返回值有参moveTo（）方法
    public void moveTo(int x,int y){
       this.x=x-this.width/2;//根据鼠标x坐标获取英雄机y坐标
       this.y=y-this.height/2;//根据鼠标y坐标获取英雄机y坐标
    }
    //创建获得双倍火力数量方法
    public int addDouble_fire(){
        return double_fire+=2;
    }
    //创建获得生命方法
    public void addLife(){
        life++;
    }
    //创建获得生命值方法
    public int getLife(){
        return life;
    }
    //创建越界方法
     @Override
    public boolean outOfBounds(){
        return false;
    }
    //创建减命方法
    public void subtractLife(){
        life--;
    }
    //创建火力值清除方法
    public void setDouble_Fire(int Double_Fire){
        this.double_fire=Double_Fire;
    }
    //创建hit方法
    public boolean hit(FlyingObject other){
        int x1=other.x-this.width/2;
        int x2=other.x+other.width+this.width/2;
        int y1=other.y-this.height/2;
        int y2=other.y+other.height+this.height/2;
        int heroCentreX=this.x+this.width/2;
        int heroCentreY=this.x+this.height/2;
        return heroCentreX>x1 && heroCentreX<x2 && heroCentreY>y1 && heroCentreY<y2;
    }
}
