package com.efan.controller.inputs;


import java.sql.Timestamp;

/**
 * 订单input
 */
public class OrderInput {

    //消费者名称
    public   String consumerName;
    //手机
    public  String mobile;
    //用户唯一id
    public  String userKey;

    //点位名称
    public  String pointName;
    //包厢Id
    public    String boxId;
    //包房编号
    public String boxName;
    //购买时间段
    public Timestamp fromTime;
    //购买时间段
    public Timestamp toTime;
    //订单类型
    public  Integer orderType;
    //金额
    public  double amount;
    public  String orderId;
}
