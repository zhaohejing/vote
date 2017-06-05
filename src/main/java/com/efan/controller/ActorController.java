package com.efan.controller;

import com.efan.appservice.iservice.IActorService;
import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.dtos.ActorDto;
import com.efan.controller.dtos.VoteDto;
import com.efan.controller.inputs.ActorInput;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Actor;
import com.efan.core.primary.Record;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报名者controller
 */
/**
 * 报名者接口
 */
@RestController
@RequestMapping("/api/actor")
public class ActorController {
    private IActorService    _actorService;
    @Autowired
    public ActorController(IActorService actorService){
        _actorService=actorService;
    }
    /**
     * 获取报名者列表带分页*/
    @ApiOperation(value="获取报名者列表带分页", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ActorInput")
    @RequestMapping(value  ="/actors" ,method = RequestMethod.POST)
    public ActionResult Actors(@RequestBody ActorInput input){
        ResultModel<Actor> result=_actorService.Actors(input);
        return  new ActionResult(result);
    }
    /**
     * 添加报名者*/
    @ApiOperation(value="添加报名者", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ActorDto")
    @RequestMapping(value  ="/modify" ,method = RequestMethod.POST)
    public ActionResult Modify(@RequestBody ActorDto input){
        Actor result=_actorService.Modify(input);
        return  new ActionResult(result);
    }
    /**
     * 删除报名者*/
    @ApiOperation(value="删除报名者", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/delete" ,method = RequestMethod.POST)
    public ActionResult Delete(@RequestBody DeleteInput input){
        _actorService.Delete(input);
        return  new ActionResult(1);
    }
    /**
     * 获取报名者详情*/
    @ApiOperation(value="获取报名者详情", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/detail" ,method = RequestMethod.POST)
    public ActionResult Detail(@RequestBody DeleteInput input){
        Actor model=_actorService.Actor(input);
        return  new ActionResult(model);
    }
    /**
     * 投票*/
    @ApiOperation(value="投票", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "VoteDto")
    @RequestMapping(value  ="/vote" ,method = RequestMethod.POST)
    public ActionResult Vote(@RequestBody VoteDto input){
       Boolean can=_actorService.CanVote(input);
       if (!can){
           return  new ActionResult(false,"今天已经投票了,请明天再试试");
       }
        Record model=_actorService.Vote(input);
        return  new ActionResult(model);
    }


    /**
     * 获取榜单列表*/
    @ApiOperation(value="获取榜单列表", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "{ 票数排行 sort传 actorCount ,卡传giftCount  }", required = true, dataType = "ActorInput")
    @RequestMapping(value  ="/sortactors" ,method = RequestMethod.POST)
    public ActionResult SortActors(@RequestBody ActorInput input){
        ResultModel<Actor> result=_actorService.Actors(input);
        return  new ActionResult(result);
    }
}
