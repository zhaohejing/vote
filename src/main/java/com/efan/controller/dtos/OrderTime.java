package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/5/9.
 */
public class OrderTime {
    public  OrderTime(Integer from,Integer to,Integer less){
        this.fromtime=from;
        this.toTiome=to;
        this.lessTime=less;
    }
    //开始时间
    public  Integer fromtime;

    //截至时间
    public  Integer toTiome;
    //剩余时间
    public  Integer lessTime;
}
