package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/5/9.
 */
public class OrderType  {
    public  OrderType(String order,Double old ,Double now){
        this.orderName=order;
        this.oldPrice=old;
        this.nowPrice=now   ;
    }
    public  String orderName;
    public  Double oldPrice;
    public  Double nowPrice;
}
