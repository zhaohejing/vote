package com.efan.appservice.service;

import com.efan.appservice.iservice.IGiftService;
import com.efan.controller.dtos.GiftDto;
import com.efan.controller.dtos.SendDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.*;
import com.efan.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 *礼物创建相关
 */
@Service
public class GiftService implements IGiftService {

    private IGiftRepository _giftRepository;
    private IGivingRepository _givingRepository;
    private IActivityRepository _activityRepository;
    private IRecordRepository _recordRepository;
    private IActorRepository _actorRepository;

    @Autowired
    public GiftService(IGiftRepository giftRepository,IGivingRepository givingRepository, IRecordRepository recordRepository,
                       IActivityRepository activityRepository,IActorRepository actorRepository){
        this._giftRepository=giftRepository;
        _givingRepository=givingRepository;
        _recordRepository=recordRepository;
        _activityRepository=activityRepository;
        _actorRepository=actorRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Gift> Gifts(BaseInput input){
        //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Gift> res=  _giftRepository.findAllByGiftNameContains(input.getFilter(), pageable);
        return  new ResultModel<>(res.getContent(),res.getTotalElements());
    }
    /*获取活动列表分页数据*/
    public ResultModel<Gift> GiftsByActivityId(DeleteInput input){
        List<Gift> list=_giftRepository.findAllByActivityId(input.id);
        return  new ResultModel<>(list);
    }
    /*获取详情*/
    public Gift Gift(DeleteInput input){
        Gift result
                =  _giftRepository.findOne(input.id);
        return  result;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _giftRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Gift   Modify(GiftDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Gift model;
        if (input.id !=null&&input.id>0){
            model=_giftRepository.findOne(input.id  );
            model.setActivityId(input.activityId);
            model.setBeVote(input.beVote);
            model.setGiftName(input.giftName);
            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setPrice(input.price);
            model=  _giftRepository.saveAndFlush(model );
            return model;
        }else {
            model=new Gift();
            model.setPrice(input.price);
            model.setBeVote(input.beVote);
            if (! input.imageUrl.isEmpty()){
                model.setImageName(input.imageName);
                model.setImageUrl(input.imageUrl);
            }
            model.setActivityId(input.activityId);
            model.setGiftName(input.giftName);
            model.setDelete(false);
            model.setId(0L);
            model.setCreationTime(df.format(new Date()));

          model=  _giftRepository.save(model);
          return  model;
        }
    }
    @Transactional
    //送礼物
  public  Giving SendGift(SendDto dto) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Gift gift=_giftRepository.findOne(dto.giftId);
        if (    gift==null){
            throw new Exception("礼物不存在");
        }
        Activity act=_activityRepository.findOne(dto.activityId);
        if (    act==null   ){
            throw new Exception("活动不存在");
        }
        Actor tor=_actorRepository.findOne(dto.actorId);
        if (    tor==null   ){
            throw new Exception("送礼对象不存在");
        }
            Giving model=new Giving();
            model.setActorId(dto.actorId);
            model.setCreationTime(df.format(new java.util.Date()));
            model.setDelete(false);
            model.setGiftId(dto .giftId);
            model.setId(0L);
            model.setSendImage(dto.sendImage);
            model.setSendKey(dto.sendKey);
            model.setSendName(dto.sendName);
         Record record=  addrecord(dto.actorId,dto.sendKey,gift.getBeVote());
         if (   record==null    ){
             throw new Exception("创建失败");
         }
            act.setTotalVotes(gift.getBeVote()+act.getTotalVotes());
         tor.setGiftCount(tor.getGiftCount()+gift.getBeVote());
         tor.setTotalPrice(tor.getTotalPrice()+(gift.getPrice()/100));
         _actorRepository.saveAndFlush(tor);
          _activityRepository.saveAndFlush(act);
        return  _givingRepository.save(model);
    }
private  Record  addrecord(Long actorId,String sendKey,Integer votes){
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    Record dto=new Record();
    dto.setActorId(actorId);
    dto.setCreationTime(df.format(new java.util.Date()));
    dto.setDelete(false);
    dto.setGift(true);
    dto.setId(0L);
    dto.setSendKey(sendKey);
    dto.setVotes(votes);
    return   _recordRepository.saveAndFlush(dto);
}

}
