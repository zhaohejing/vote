package com.efan.controller.dtos;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by 45425 on 2017/6/2.
 */
public class ActivityDto  {

    public  String content;
    public Long id;
    public  String title;
    public Timestamp endTime;
    public  String rules;
    public List<ImageDto> images;


}
