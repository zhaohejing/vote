package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/6/8.
 */
public class OrderDto {
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
