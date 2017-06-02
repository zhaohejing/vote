package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/5/10.
 */
public class SongDto {
    public  SongDto(Integer id,String  name){
        this.songId=id  ;
        this.songName=name;
    }
    public  Integer songId;
    public  String songName;

}
