package com.company;

import java.awt.image.BufferedImage;

/**
 * 创建公共类
 * 分析在射击游戏中可以抽出公共特征和方法:
 * 特征:宽（width）、高（height）、坐标（x,y）、图片（image）;
 * 方法:（step）、出边界方法（outOfBounds）;
 */
public  abstract class FlyingObject {
    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected BufferedImage image;
    public abstract void step();
    public boolean shootBy(Bullet bullet){
        int x=bullet.x;
        int y=bullet.y;
        return x>this.x && x<this.x+width && y>this.y && y<this.y+height;
    }
    protected abstract boolean outOfBounds();
}
