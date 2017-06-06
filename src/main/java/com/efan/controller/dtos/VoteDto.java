package com.efan.controller.dtos;


/**
 * 投票dto
 */
public class VoteDto {

    //"投票人key")
    public   String sendKey;
    //"接受人")
    public Long actorId;
    //票数
    public  Integer votes;
    public Long activityId;
}
