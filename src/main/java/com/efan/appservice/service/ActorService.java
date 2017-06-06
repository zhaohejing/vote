package com.efan.appservice.service;

import com.efan.appservice.iservice.IActorService;
import com.efan.controller.dtos.ActorDto;
import com.efan.controller.dtos.VoteDto;
import com.efan.controller.inputs.ActorInput;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.core.primary.Actor;
import com.efan.core.primary.Record;
import com.efan.repository.IActivityRepository;
import com.efan.repository.IActorRepository;
import com.efan.repository.IRecordRepository;
import com.efan.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Service
public class ActorService implements IActorService {
    private IActorRepository _actorRepository;
    private IRecordRepository _recordRepository;
private IActivityRepository _activityRepository;

    @Autowired
    public ActorService(IActorRepository actorRepository,IRecordRepository recordRepository,IActivityRepository activityRepository){
        this._actorRepository=actorRepository;
        _recordRepository=recordRepository;
        _activityRepository=activityRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Actor> Actors(ActorInput input){
       Sort sort = new Sort(Sort.Direction.DESC, input.getSort());
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),sort);
        Page<Actor> res=  _actorRepository.findAllByActivityIdAndActorNameContains(input.getActivityId(), input.getFilter(), pageable);
        return  new ResultModel<>( res.getContent(),res.getTotalElements());
    }
    /*获取详情*/
    public Actor Actor(DeleteInput input){
        Actor result=  _actorRepository.findOne(input.id);
        return  result;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _actorRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Actor   Modify(ActorDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Actor model;
        if (input.id !=null&&input.id>0){
            model=_actorRepository.findOne(input.id);
            model.setActivityId(input.activityId);
            model.setActorCount(input.actorCount);
            model.setActorImage(input.actorImage);
            model.setActorKey(input.actorKey);
            model.setActorName(input.actorName);
            model=  _actorRepository.saveAndFlush(model );
            return model;
        }else {
            model=new Actor();
            model.setActivityId(input.activityId);
            model.setActorCount(0);
            model.setActorImage(input.actorImage);
            model.setActorKey(input.actorKey);
            model.setActorName(input.actorName);
            model.setId(0L);
            model.setCreationTime(df.format(new java.util.Date()));
            model=  _actorRepository.save(model);
            return  model;
        }

    }
    //投票
    public Record  Vote(VoteDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Record dto=new Record();
        dto.setActorId(input.actorId);
        dto.setCreationTime(df.format(new java.util.Date()));
        dto.setDelete(false);
        dto.setGift(false);
        dto.setId(0L);
        dto.setSendKey(input.sendKey);
        dto.setVotes(input.votes);
Activity act=_activityRepository.findOne(input.activityId);
if (    act==null   ){
    return null;
}
      Record red=   _recordRepository.saveAndFlush(dto);
if (    red!=null){
    act.setActorCount(act.getActorCount()+1);
    _activityRepository.saveAndFlush(act);
}
return  red;

    }
    public  Boolean  CanVote(VoteDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        java.util.Date curr=new java.util.Date();
        java.util.Date start= DateUtil.GenderTime(curr,true);
        List<Record> res=_recordRepository.findBySendKeyAndActorIdAndCreationTimeBetween(input.sendKey,input.actorId,start,curr);
        return  res.size()<0;
    }




}
