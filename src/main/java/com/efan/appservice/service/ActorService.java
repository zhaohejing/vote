package com.efan.appservice.service;

import com.efan.appservice.iservice.IActorService;
import com.efan.controller.OutPuts.GivingOutPut;
import com.efan.controller.dtos.ActorDto;
import com.efan.controller.dtos.VoteDto;
import com.efan.controller.inputs.*;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.*;
import com.efan.repository.*;
import com.efan.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class ActorService implements IActorService {
    private IActorRepository _actorRepository;
    private IRecordRepository _recordRepository;
    private IActivityRepository _activityRepository;
    private IGivingRepository _givingRepository;
    private IGiftRepository _giftRepository;
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    public JdbcTemplate _jdbc;
    @Autowired
    public ActorService(IActorRepository actorRepository,IRecordRepository recordRepository,IActivityRepository activityRepository,IGivingRepository givingRepository
    , IGiftRepository giftRepository){
        this._actorRepository=actorRepository;
        _recordRepository=recordRepository;
        _activityRepository=activityRepository;
    _givingRepository=givingRepository;
    _giftRepository=giftRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Actor> Actors(ActorInput input){
       Sort sort = new Sort(Sort.Direction.DESC, input.getSort());
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),sort);
        Page<Actor> res=  _actorRepository.findAllByActivityIdAndActorNameContains(input.getActivityId(), input.getFilter(), pageable);
        return  new ResultModel<>( res.getContent(),res.getTotalElements());
    }

  public ResultModel<Map<String,Object>> GetActorsByHot(ActorInput input){
      StringBuilder sql=new StringBuilder();
      StringBuilder count=new StringBuilder();
      sql.append("select *,actor_count+gift_count totalCount from actor  where 1=1  ");
      count.append("SELECT count(*) from actor where 1=1");

      if (input.getFilter()!=null&& !input.getFilter().isEmpty()){
          sql.append(" and actor_name like '%"+input.getFilter()+"%' ");
          count.append(" and actor_name like '%"+input.getFilter()+"%' ");
      }
      if (input.getActivityId()!=null&& input.getActivityId()>0){
          sql.append(" and activity_id="+input.getActivityId());
          count.append(" and activity_id="+input.getActivityId());
      }
    sql.append(" order by actor_count+gift_count desc ");

      sql.append(" limit  "+input.getPage()+" , "+input.getSize() );
      Long total= _jdbc.queryForObject(count.toString(),Long.class);
      List<Map<String,Object>> list = _jdbc.queryForList(sql.toString());
      return  new ResultModel<Map<String,Object>>(list,total);
  }

    /*获取详情*/
    public Actor Actor(DeleteInput input){
        Actor a=  _actorRepository.findOne(input.id);

        return  a;
    }
    public ResultModel<GivingOutPut> GetActorGifts(GivingInput input){
        Actor a=  _actorRepository.findOne(input.actorId);
        //Sort sort = new Sort(Sort.Direction.DESC, input.getSort());
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize());
        Page<Giving> givings=_givingRepository.findAllByActorId(input.actorId,pageable);
        List<Gift> gifts=_giftRepository.findAllByActivityId(input.activityId);
        List<GivingOutPut> givingOutPuts =new ArrayList<>();

        if (givings.getTotalElements()>0) {
            Long length = givings.getTotalElements();
            for (int i = 0; i < length; i++) {
                GivingOutPut dto = new GivingOutPut();
                Giving temp = givings.getContent().get(i);
                dto.setActorId(temp.getActorId());
                dto.setActorName(a.getActorName());
                dto.setCreationTime(temp.getCreationTime());
                dto.setSendImage(temp.getSendImage());
                dto.setSendKey(temp.getSendKey());
                dto.setSendName(temp.getSendName());
                Gift tg = FindByFilter(gifts, temp.getGiftId());
                if (tg != null) {
                    dto.setGiftId(tg.getId());
                    dto.setGiftName(tg.getGiftName());
                    dto.setGiftImage(tg.getImageUrl());
                }
                givingOutPuts.add(dto);
            }
        }
          return new ResultModel<GivingOutPut>(givingOutPuts,givings.getTotalElements());
    }
    private  Gift FindByFilter(List<Gift> list,Long giftId){
        Gift te=null;
        if (list.isEmpty())return  te;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId()!=giftId){
              continue;
            }else   {
                te= list.get(i);
            }
        }
        return  te;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _actorRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Actor   Modify(ActorDto input) throws  Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Actor model;
        List<Actor>  actors=_actorRepository.findAllByActivityId(input.activityId);
                Integer count=actors.size();
        if (input.id !=null&&input.id>0){
            model=_actorRepository.findOne(input.id);
            model.setActivityId(input.activityId);
            model.setActorCount(0);
            model.setActorImage(input.actorImage);
            model.setDeclaration(input.declaration);
            model.setActorKey(input.actorKey);
            model.setActorName(input.actorName);
            model=  _actorRepository.saveAndFlush(model );
        }else {
            model=new Actor();
            model.setActivityId(input.activityId);
            model.setActorCount(0);
            model.setSort(count+1);
            model.setActorImage(input.actorImage);
            model.setDeclaration(input.declaration);
            model.setActorKey(input.actorKey);
            model.setActorName(input.actorName);
            model.setId(0L);
            model.setCreationTime(df.format(new java.util.Date()));
            model=  _actorRepository.save(model);
        }
        if (model==null) throw new Exception("报名失败");
        Activity act=_activityRepository.findOne(input.activityId);
        act.setActorCount(act.getActorCount()+1);
        _activityRepository.saveAndFlush(act);
        return  model;
    }
    //投票
    public Record  Vote(VoteDto input) throws Exception{
        Actor tor=_actorRepository.findOne(input.actorId);
        if(!tor.getCanVote()){
            throw new Exception("热度已满");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Record dto=new Record();
        dto.setActorId(input.actorId);
        dto.setCreationTime(df.format(new java.util.Date()));
        dto.setDelete(false);
        dto.setGift(false);
        dto.setId(0L);
        dto.setActivityId(input.activityId);
        dto.setSendKey(input.sendKey);
        dto.setVotes(input.votes);
Activity act=_activityRepository.findOne(input.activityId);
if (    act==null   ) throw new Exception("活动不存在");
      Record red=   _recordRepository.saveAndFlush(dto);

        if (    tor==null   ) throw new Exception("投票对象不存在");
if ( red!=null){
    tor.setActorCount(tor.getActorCount()+input.votes);
    act.setTotalVotes(act.getTotalVotes()+1);
    _activityRepository.saveAndFlush(act);
    _actorRepository.saveAndFlush(tor);
}
return  red;
    }
    public  Boolean  CanVote(VoteDto input){
        List<Record> list=_recordRepository.findAllBySendKeyAndActivityId(input.sendKey,input.activityId);
       return  list.size()<0;

    }

   public  Boolean Disable(VoteDto input){
       Actor act=_actorRepository.findOne(input.actorId);
       if (act==null) return  true;
       return  act.getCanVote();
   }

    public void   DisableVote(DisableInput input){
        List<Actor>  list= _actorRepository.findAll(input.list);
        for (int i = 0; i < list.size(); i++) {
            Actor temp=list.get(i);
            temp.setCanVote(input.state);
            _actorRepository.save(temp);
        }
    }
}
