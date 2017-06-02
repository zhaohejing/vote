package com.efan.controller.dtos;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 45425 on 2017/6/2.
 */
public class ActivityDto {
    public Long id;
    public  String title;

    public  String content;

    public Timestamp endTime;
    public  String rules;
    public List<String> images;
}
