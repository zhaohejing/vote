package com.efan.appservice.service;

import com.efan.appservice.iservice.IActorService;
import com.efan.controller.inputs.ActorInput;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Actor;
import com.efan.repository.IActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by 45425 on 2017/6/2.
 */
@Service
public class ActorService implements IActorService {
    /* @Value("${efanurl}")
  private String efanurl;
  @Value("${returnurl}")
  private String returnurl;*/
    private IActorRepository _actorRepository;
    @Autowired
    public ActorService(IActorRepository actorRepository){
        this._actorRepository=actorRepository;
    }
    /*获取活动列表分页数据*/
    public ResultModel<Actor> Actors(ActorInput input){
          Sort sort = new Sort(Sort.Direction.DESC, "actorCount");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),sort);
        Page<Actor> res=  _actorRepository.findAllByActivityIdAndActorNameLike(input.getActivityId(), input.getFilter(), pageable);
        return  new ResultModel<Actor>( res.getContent(),res.getTotalElements());
    }
    /*获取详情*/
    public Actor Actor(DeleteInput input){
        Actor res=  _actorRepository.findOne(input.id);
        return  res;
    }
    /*删除*/
    public void   Delete(DeleteInput input){
        _actorRepository.delete(input.id);
    }
    /*创建或编辑*/
    public Actor   Modify(Actor input){
        Actor model=new Actor();
        model=  _actorRepository.saveAndFlush(model );
        return  model   ;
    }
}
