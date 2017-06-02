package com.efan.controller.dtos;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by 45425 on 2017/6/2.
 */
public class ActivityDto {
    private Long id;
    private  String title;

    private  String content;

    private Timestamp endTime;
    private  String rules;
    private List<String> images;
}
