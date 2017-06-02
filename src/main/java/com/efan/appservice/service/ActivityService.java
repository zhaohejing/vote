package com.efan.appservice.service;

import com.efan.appservice.iservice.IActivityService;

import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.Response;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.core.primary.Image;
import com.efan.repository.IActivityRepository;
import com.efan.repository.IImageRepository;
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

    private IActivityRepository _activityRepository;
    private IImageRepository _imageRepository;
    @Autowired
     public ActivityService(IActivityRepository activityRepository,IImageRepository imageRepository){
         this._activityRepository=activityRepository;
         _imageRepository=imageRepository;
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Activity model;
        if (input.id>0){
            model=_activityRepository.findOne(input.id);
            model.setContent(input.content);
            model.setEndTime(input.endTime);
            model.setRules(input.rules);
            model.setTitle(input.title);
            model=  _activityRepository.saveAndFlush(model);
        }else {
            model=new Activity();
            model.setContent(input.content);
            model.setEndTime(input.endTime);
            model.setRules(input.rules);
            model.setTitle(input.title);
            model.setActorCount(0);
            model.setDelete(false);
            model.setActorCount(0);
            model.setTotalVotes(0);
            model.setTraffic(0);
            model.setId(0L);
            model.setCreationTime(df.format(new java.util.Date()));
            model=  _activityRepository.save(model);
        }
        if (input.images.size()>0){
            _imageRepository.deleteByActivityId(input.id);
            List<Image> list=new ArrayList<>();
            for (int i = 0; i < input.images.size(); i++) {
                Image dto=new Image();
                dto.setActivityId(model.getId());
                dto.setId(0L);
                dto.setQiniuUrl(input.images.get(i));
                dto.setState(true);
                dto.setCreationTime(df.format(new java.util.Date()));
                   list.add(dto);
            }
              list=   _imageRepository.save(list);
        }
        return model;
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
