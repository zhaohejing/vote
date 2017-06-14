package com.efan.appservice.iservice;

import com.efan.controller.OutPuts.GivingOutPut;
import com.efan.controller.dtos.ActorDto;
import com.efan.controller.dtos.VoteDto;
import com.efan.controller.inputs.*;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.core.primary.Actor;
import com.efan.core.primary.Record;

import java.util.Map;

/**
 * 报名者
 */
public interface IActorService {
    /*获取活动列表分页数据*/
     ResultModel<Actor> Actors(ActorInput input);
    /*获取详情*/
    Actor Actor(DeleteInput input);
    ResultModel<GivingOutPut> GetActorGifts(GivingInput input);
    /*删除*/
     void   Delete(DeleteInput input);
    /*创建或编辑*/
     Actor   Modify(ActorDto input) throws  Exception;
    //投票
     Record  Vote(VoteDto input) throws Exception;
    //是否可以投票
    Boolean  CanVote(VoteDto input);
    ResultModel<Map<String,Object>> GetActorsByHot(ActorInput input);
   void   DisableVote(DisableInput input);
    Boolean Disable(Long actorId);
    Activity AddActors(MutileActorInput input) throws Exception;
}
