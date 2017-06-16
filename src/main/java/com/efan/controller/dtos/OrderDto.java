package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/6/8.
 */
public class OrderDto {
    public  OrderDto(){}
    public  OrderDto(String order,String openId,Long giftId,String giftName,Integer price,String des,String payname,String payImage,Long actorId,Long activityId){
        this.orderDes=des;
        this.orderNum=order;
        this.payKey=openId;
        this.productId=giftId;
        this.productName=giftName;
        this.price=price;
        this.payName=payname;
        this.payImage=payImage;
        this.actorId=actorId;
        this.activityId=activityId;
    }
    //购买人唯一id
    public String payName;
    public  Long activityId;
    //购买人唯一id
    public String payImage;

    public  Long actorId;

    public  String orderNum;
    //用户唯一id
    //购买人唯一id
    public String payKey;
    //商品Id
    public  Long productId;
    //商品名
    public  String productName;
    //订单描述
    public String  orderDes;
    //价格单位为分
    public Integer  price;

}
