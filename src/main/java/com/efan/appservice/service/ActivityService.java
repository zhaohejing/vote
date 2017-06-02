package com.efan.appservice.service;

import com.efan.appservice.iservice.IActivityService;

import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.Response;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.repository.IActivityRepository;
import com.efan.utils.HttpUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *活动创建相关
 */
@Service
public class ActivityService implements IActivityService {
   /* @Value("${efanurl}")
    private String efanurl;
    @Value("${returnurl}")
    private String returnurl;*/
    private IActivityRepository _activityRepository;
    @Autowired
     public ActivityService(IActivityRepository activityRepository){
         this._activityRepository=activityRepository;
     }
     /*获取活动列表分页数据*/
    public ResultModel<Activity> Activitys(BaseInput input){
      //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Activity> res=  _activityRepository.findAllByTitleLike(input.getFilter(), pageable);
        return  new ResultModel<Activity>( res.getContent(),res.getTotalElements());
    }
    /*获取详情*/
    public Activity Activity(DeleteInput input){
        Activity res=  _activityRepository.findOne(input.id);
        return  res;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _activityRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Activity   Modify(ActivityDto input){
       Activity model=new Activity();
        model=  _activityRepository.saveAndFlush(model );
        return  model   ;
    }

    private  Date GenderTime(Date time,Boolean isstart){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        if (isstart){
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        }else   {
            calendar.set(Calendar.HOUR,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            calendar.set(Calendar.MILLISECOND,999);
        }
        return  calendar.getTime();
    }
    private  String getTimeDifference(Timestamp a, Timestamp b) {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
        long t2 = 0L;
        try {
            t1 = timeformat.parse(getTimeStampNumberFormat(a)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            t2 = timeformat.parse(getTimeStampNumberFormat(b)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
        int hours=(int) ((t1 - t2)/3600000);
        int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
        int second=(int) ((t1 - t2)/1000-hours*3600-minutes*60);
        return ""+  hours*60+minutes+(second/60);
    }
    private  String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
}
