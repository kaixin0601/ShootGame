package com.company;

/**
 * 创建小蜜蜂奖励接口
 *小蜜蜂的奖励有两种:双倍火力和增加生命
 */
public interface Award {
    public static final int DOUBLE_FIRE=0;
    public static final int LIFE=1;
    /*
    奖励获取类型方法
     */
    int getType();
}
