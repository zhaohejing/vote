package com.efan.controller.dtos;

/**
 * Created by 45425 on 2017/5/8.
 */
public class RemoteDto {
    public  RemoteDto(int id,String name,String address,Integer distance,int parentId){
        this.id =id;
        this.name=name;
        this.address=address;
        this.distance=distance;
        this.parentId=parentId;
    }
    public  Integer id;
    public  String name;
    public  String address;
    public Integer  distance;
    public  Integer parentId;
}
