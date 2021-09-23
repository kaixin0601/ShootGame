package com.company;
/**
 * 分析射击游戏有特征:英雄机类（Hero）、敌机类（Airplane）、小蜜蜂类（Bee）、子弹类（Bullet）
 * 分析在射击游戏中可以抽出公共特征和方法:宽（width）、高（height）、坐标（x,y）、图片（image）、（step）、出边界方法（outOfBounds）
 * 流程步骤如下:
 * 1、设计一个400X654的界面；
 * 2、定义需要加载的图片
 * 3、静态资源加载
 * 4、在显示面板中增加图片（英雄机、敌机、小蜜蜂、子弹）
 * 5、创建公共类:FlyingObject
 *    包含特征:宽（width）、高（height）、坐标（x,y）、图片（image）;
 *    方法（step）、出边界方法（outOfBounds）
 * 6、创建5个类:英雄机类（Hero）;自己的特征为:火力、生命和双图片交换;
 *            敌机类（Airplane）、小蜜蜂类（Bee）、子弹类（Bullet）
 * 7、在框上绘制静态的英雄机、飞行物（敌机和小蜜蜂）、和子弹
 * 8、让静态的飞行物动起来
 *      （1） 英雄机移动
 *      （2）飞行物入场
 *      （3）飞行物走步
 *      （4）子弹射击
 *      （5）击中删除击中飞行物和子弹
 *      （6）删除越界飞行物和子弹,在父类FlyingObject中创建outOfBounds方法
 * 9、绘制分数和生命值
 * 10、在ShootGame中添加isGameOver方法,该方法用于判断游戏是否结束
 * 11、在ShootGame中添加checkGameOverAction方法,该方法用于判断游戏是否已经结束,如果游戏已经结束,则游戏状态设置为GAME_OVER
 * 12、修改ShootGame中的Action方法,添加鼠标点击、移入、退出等操作的状态处理
 * 13、在ShootGame中添加paintState方法
 */
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ShootGame extends JPanel {//需要导入import javax.swing.JPanel;包
    /*
    1、定义一个宽400和长654
     */
    public static final int WIDTH=400;
    public static final int HEIGHT=654;
    /*
    2、定义需要加载的图片
       需要导入import java.awt.image.BufferedImage;包
     */
    public static BufferedImage background;
    public static BufferedImage airplane;
    public static BufferedImage bee;
    public static BufferedImage bullet;
    public static BufferedImage gameover;
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage pause;
    public static BufferedImage start;


    private Hero hero=new Hero();//创建英雄机对象
    private FlyingObject [] flys={ };//创建飞行物对象
    private Bullet [] bullets={};
/*    public ShootGame(){
        flys=new FlyingObject[2];
        flys[0]=new Bee();
        flys[1]=new Airplane();
        bullets=new Bullet[1];
        bullets[0]=new Bullet(195,350);
    }*/
    /*
    3、静态资源加载
      需要导入import javax.imageio.ImageIO;import java.io.IOException;包
     */
    static {
        try {
            background  = ImageIO.read(ShootGame.class.getResource("background.png"));
            airplane    = ImageIO.read(ShootGame.class.getResource("airplane.png"));
            bee         = ImageIO.read(ShootGame.class.getResource("bee.png"));
            bullet      = ImageIO.read(ShootGame.class.getResource("bullet.png"));
            gameover    = ImageIO.read(ShootGame.class.getResource("gameover.png"));
            hero0       = ImageIO.read(ShootGame.class.getResource("hero0.png"));
            hero1       = ImageIO.read(ShootGame.class.getResource("hero1.png"));
            pause       = ImageIO.read(ShootGame.class.getResource("pause.png"));
            start       = ImageIO.read(ShootGame.class.getResource("start.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
    4、在显示面板中增加图片
       需要导入import java.awt.Graphics;包
     */
    public void paint(Graphics g){
        //绘制背景图
        g.drawImage(background,0,0,null);
        //绘制英雄机
        paintHero(g);
        //绘制飞行物
        paintFlyings(g);
        //绘制子弹
        paintBullet(g);
        //绘制分数和生命
        paintScore(g);
        //绘制游戏状态
        paintState(g);
    }
    //绘制英雄机方法
    public void paintHero(Graphics g){
        g.drawImage(hero.image,hero.x,hero.y,null);
    }
    //绘制飞行物
    public void paintFlyings(Graphics g){
        for(int i=0;i<flys.length;i++){
            FlyingObject f=flys[i];
            g.drawImage(f.image,f.x,f.y,null);
        }
    }
    //绘制子弹
    public void paintBullet(Graphics g){
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            g.drawImage(b.image,b.x,b.y,null);
        }
    }
    //绘制分数和生命
    public void paintScore(Graphics g){
        int x=10;//定义显示分数和生命值的x的位置
        int y=20;//定义显示分数和生命值的y的位置
        g.setColor(Color.blue);
        Font font=new Font(Font.SANS_SERIF,Font.BOLD,20);
        g.setFont(font);
        g.drawString("SCORE:"+score,x,y);//绘制分数
        g.drawString("LIFE:"+hero.getLife(),x,y+20);//绘制生命值
    }
    //绘制游戏状态
    public void paintState(Graphics g){
        switch(state){
            case START:
                g.drawImage(start,0,0,null);
                break;
            case PAUSE:
                g.drawImage(pause,0,0,null);
                break;
            case GAME_OVER:
                g.drawImage(gameover,0,0,null);
                break;
        }
    }
    /*
    静态画面动起来
    1、创建action方法
    2、创建定时器对象
    3、间隔10ms执行一次run()方法
    4、创建飞行物进场方法enterFlyings(),创建随机生成飞行物方法nextOne()
    5、创建飞行物走步方法stepAction()
    6、创建射击方法shootAction()
    7、创建击中方法BangAction()
     */

   //随机生成飞行物方法
    public static FlyingObject nextOne(){
        Random ran=new Random();
        int type=ran.nextInt(20);
        if(type<5){
            return new Bee();
        }
        else{
            return new Airplane();
        }
    }
    //飞行物进场方法
    int flyingEnteredIndex=0;//飞行物入场计数
    public void enterFlyings(){
        flyingEnteredIndex++;//飞行物开始计数
        if(flyingEnteredIndex%40==0){//每计数40次（400ms）生成一个飞行物
            FlyingObject obj=nextOne();//随机生成飞行物
            flys=Arrays.copyOf(flys,flys.length+1);//飞行物数组扩容
            flys[flys.length-1]=obj;//将随机生成的飞行物obj放入到数组的最后一位
        }
    }

    //飞行物走步方法
    public void stepAction(){
        //飞行物走步
        for(int i=0;i<flys.length;i++){
            flys[i].step();
        }
        //子弹走步
        for(int i=0;i<bullets.length;i++){
            bullets[i].step();
        }
        //英雄机走步
        hero.step();
    }
    //射击方法
    private int shootActionIndex=0;//射击计数
    public void shootAction(){
        shootActionIndex++;
        if(shootActionIndex%30==0){//每计数30次（300ms）生成一个子弹
            Bullet [] bs=hero.shoot();
            bullets=Arrays.copyOf(bullets,bullets.length+bs.length);//将子弹数组扩容
            System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
        }
    }
    /*
    击中
    在飞行物类中增加一个击中方法shootBy(Bullet bullet)
    子弹击中飞行物消除子弹和飞行物
    子弹击中小蜜蜂掉落双倍火力或者生命奖励
    子弹击中敌机获得分数
     */
    private int score=0;//定义一个分数,初始值为0
    public void bang(Bullet bullet){
        int index=-1;//索引初始值为-1;表示未击中
        for(int i=0;i<flys.length;i++){
            FlyingObject obj=flys[i];
            if(obj.shootBy(bullet)){
                index=i;//击中索引
                break;
            }
        }
        /*将击中的飞行物索引出来,删除*/
        if(index!=-1){
            FlyingObject one=flys[index];
            FlyingObject temp=flys[index];
            flys[index]=flys[flys.length-1];
            flys[flys.length-1]=temp;
            flys=Arrays.copyOf(flys,flys.length-1);
            if(one instanceof Emeny){
                Emeny e=(Emeny) one;
                score+=e.getScore();
            }
            if(one instanceof Award){
                Award a=(Award) one;
                int type=a.getType();
                switch (type){
                    case Award.DOUBLE_FIRE:
                        hero.addDouble_fire();
                    case Award.LIFE:
                        hero.addLife();
                }
            }
        }
    }
    /*
    击中方法

     */
    public void bangAction(){
        for(int i=0;i<bullets.length;i++){
            Bullet b=bullets[i];
            bang(b);
        }
    }
    /*
    创建越界删除方法
    outOfBoundsAction()
     */
    public void outOfBoundsAction() {
        int index=0;
        FlyingObject [] flyingLives=new FlyingObject[flys.length];
        for(int i=0;i<flys.length;i++) {
            FlyingObject f=flys[i];
            if(!f.outOfBounds()) {
                flyingLives[index++]=f;
            }
        }
        flys=Arrays.copyOf(flyingLives,index);
        index=0;
        Bullet [] bulletLives=new Bullet[bullets.length];
        for(int i=0;i<bullets.length;i++) {
            Bullet b=bullets[i];
            if(!b.outOfBounds()) {
                bulletLives[index++]=b;
            }
        }
        bullets=Arrays.copyOf(bulletLives, index);
    }
    /*
        创建isGameOver方法
     */
    public boolean isGameOver(){
        int index=-1;
        for(int i=0;i<flys.length;i++){
            FlyingObject obj=flys[i];
            if(hero.hit(obj) ){
                hero.addDouble_fire();
                hero.subtractLife();
                index=i;
            }
            if(index!=-1){
                FlyingObject f=flys[index];
                flys[index]=flys[flys.length-1];
                flys[flys.length-1]=f;
                flys=Arrays.copyOf(flys,flys.length-1);
            }
        }
        return hero.getLife() <=0;
    }
    /*
    实现游戏的开始、运行、停止和暂停
     */
    //1、在ShootGame中添加以下属性
    private int state;
    public static final int START=0;
    public static final int RUNNING=1;
    public static final int PAUSE=2;
    public static final int GAME_OVER=3;
    //checkGameOverAction方法
    public void checkGameOverAction(){
        if(isGameOver()){
            state=GAME_OVER;
        }
    }
    private Timer timer;//导入import javax.swing.Timer;包
    private int interval=10;//间隔10ms
    public void action(){
        /*
        英雄机跟随鼠标移动
        在英雄机类中增加无返回值有参moveTo（）方法
         */
        MouseAdapter mouse=new MouseAdapter() {//导入import java.awt.event.MouseAdapter;包
            public void mouseMoved(MouseEvent e){//导入import java.awt.event.MouseEvent;包
                /*
                 修改ShootGame中的Action方法,添加鼠标点击、移入、退出等操作的状态处理
                 */
                if(state==RUNNING){
                    int x=e.getX();
                    int y=e.getY();
                    hero.moveTo(x,y);
                }
            }
            //鼠标移入操作
            public void mouseEntered(MouseEvent e) {
                if(state==PAUSE){//暂停时运行
                    state=RUNNING;
                }
            }
            //鼠标点击操作
            public void mouseClicked(MouseEvent e) {
                switch(state){
                    case START:
                        state=RUNNING;
                        break;
                    case GAME_OVER:
                        flys=new FlyingObject[0];
                        bullets=new Bullet[0];
                        hero=new Hero();
                        state=START;
                        break;
                }
            }
            //鼠标退出操作
            public void mouseExited(MouseEvent e) {
                if(state!=GAME_OVER){
                    state=PAUSE;
                }
            }
        };this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        timer=new Timer();//创建定时器对象
        //如下代码,每隔10ms执行一次run()方法
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(state==RUNNING){
                    //飞行物入场,敌机和小蜜蜂开始准备进入画框
                    enterFlyings();
                    //飞行物走步,敌机和小蜜蜂开始掉落
                    stepAction();
                    //射击
                    shootAction();
                    //击中
                    bangAction();
                    //出界
                    outOfBoundsAction();
                    //检查游戏结束
                    checkGameOverAction();
                    //重新绘制
                    repaint();
                }
            }
        },interval,interval);
    }
    public static void main(String[] args) {
	// write your code here
        /*
        制作一个显示框
        需要导入import javax.swing.JFrame;包
         */
        JFrame frame=new JFrame("Fly-杨辉");
        ShootGame game=new ShootGame();//创建ShootGame对象
        frame.add(game);//将画面加入到框上
        frame.setSize(WIDTH,HEIGHT);//设置框的大小
        frame.setAlwaysOnTop(true);//设置框总在上
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭停止运行
        frame.setLocationRelativeTo(null);//设置框在显示中心
        frame.setVisible(true);//设置框可见
        game.action();
    }
}
