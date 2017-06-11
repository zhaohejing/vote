package com.efan.appservice.service;

import com.efan.appservice.iservice.IActivityService;

import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.OutPuts.ActivityOutPut;
import com.efan.controller.dtos.ImageDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.controller.inputs.ListInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.core.primary.Image;
import com.efan.core.primary.Prize;
import com.efan.repository.IActivityRepository;
import com.efan.repository.IImageRepository;
import com.efan.repository.IPrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *活动创建相关
 */
@Service
public class ActivityService implements IActivityService {

    private IActivityRepository _activityRepository;
    private IImageRepository _imageRepository;
    private IPrizeRepository _prizeRepository;


    @Autowired
     public ActivityService(IActivityRepository activityRepository,IImageRepository imageRepository,IPrizeRepository prizeRepository){
         this._activityRepository=activityRepository;
         _imageRepository=imageRepository;
         _prizeRepository=prizeRepository;

     }
     /*获取活动列表分页数据*/
    public ResultModel<Activity> Activitys(BaseInput input){
      //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Activity> res=  _activityRepository.findAllByTitleContains(input.getFilter(), pageable);
        return  new ResultModel<>( res.getContent(),res.getTotalElements());
    }
    /*获取活动列表分页数据*/
    public List<Activity> AllActivitys(){
        List<Activity> result=  _activityRepository.findAllByIsPublic(true);
        return  result;
    }
    /*获取详情*/
    public Activity Activity(DeleteInput input){
        Activity result=  _activityRepository.findOne(input.id);
        return  result;
    }
    public ActivityOutPut GetDetail(Long activityId) throws Exception{
            Activity model=_activityRepository.findOne(activityId);
            if (    model==null)
                throw  new Exception("活动不存在");
            ActivityOutPut out=new ActivityOutPut();
            out.setActorCount(model.getActorCount());
            out.setContent(model.getContent());
            out.setCreationTime(model.getCreationTime());
            out.setEndTime(model.getEndTime());
            out.setId(model.getId());
            out.setRules(model.getRules());
            out.setTitle(model.getTitle());
            out.setTotalVotes(model.getTotalVotes());
            out.setTraffic(model.getTraffic());

            List<Image> images=  _imageRepository.findAllByActivityIdOrderBySort(activityId);
            if (    images.size()>0){
                out.setImages(images);
            }
        Sort sort = new Sort(Sort.Direction.ASC, "level");
            List<Prize> prizes=_prizeRepository.findAllByActivityId(activityId);
            if (prizes.size()>0){
                out.setPrizes(prizes);
            }
            return  out;
    }

    /*增加访问量*/
    public Activity Access(DeleteInput input){
        Activity result=  _activityRepository.findOne(input.id);
        result.setTraffic(result.getTraffic()+1);
       return _activityRepository.saveAndFlush(result);
    }
    /*发布*/
    public void Public(ListInput input){
      List<Activity>  list= _activityRepository.findAll(input.list);

        for (int i = 0; i < list.size(); i++) {
            Activity temp=list.get(i);
            if (temp.getPublic()){ continue;}
            temp.setPublic(true);
               _activityRepository.saveAndFlush(temp);
        }
    }
    /*删除*/
    public void   Delete(ListInput input){

        List<Activity>  list= _activityRepository.findAll(input.list);
        for (int i = 0; i < list.size(); i++) {
            Activity temp=list.get(i);
            _activityRepository.delete(temp);
        }
    }



    /*创建或编辑*/
    public Activity   Modify(ActivityDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Activity model;
        if (input.id !=null&& input.id>0){
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
            model.setPublic(false);
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
                ImageDto img=input.images.get(i);
                Image dto=new Image();
                dto.setActivityId(model.getId());
                dto.setId(0L);
                dto.setSort(img.sort);
                dto.setUrl(img.url);
               dto.setIsTitle(img.isTitle);
               dto.setIsShare(img.isShare);
                dto.setTitle(img.title);
                dto.setState(true);
                dto.setCreationTime(df.format(new java.util.Date()));
                   list.add(dto);
            }
               _imageRepository.save(list);
        }
        return model;
    }


}
