package com.efan.controller.dtos;


/**
 * 送礼物dto
 */
public class SendDto {
    public  SendDto() {

    }
    public  SendDto(String sendKey,String sendName,String sendImage,Long activityId,Long actorId,Long giftId) {
this.sendKey=sendKey;
this.sendName=sendName;
this.sendImage=sendImage;
this.activityId=activityId;
this.actorId=actorId;
this.giftId=giftId;
    }
    public  String sendName;
    public  String sendImage;
    //"接受人")
    public Long actorId;
    //"礼物id")
    public Long giftId;
    public  String sendKey;
    public  Long activityId;
}
