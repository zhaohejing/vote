package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/5/10.
 */
public class SongCateDto {
    public  SongCateDto(Integer id,String  name){
        this.cateId=id  ;
        this.cateName=name;
    }
    public  Integer cateId;
    public  String cateName;

}
