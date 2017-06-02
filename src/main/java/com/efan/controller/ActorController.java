package com.efan.controller;

import com.efan.appservice.iservice.IActivityService;
import com.efan.appservice.iservice.IActorService;
import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import com.efan.core.primary.Actor;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 45425 on 2017/6/2.
 */
/**
 * 远程购买接口表
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
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/actors" ,method = RequestMethod.POST)
    public ActionResult Actors(@RequestBody BaseInput input){
        ResultModel<Actor> result=_actorService.Actors(input);
        return  new ActionResult(result);
    }
    /**
     * 添加报名者*/
    @ApiOperation(value="添加报名者", notes="报名者接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ActivityDto")
    @RequestMapping(value  ="/modify" ,method = RequestMethod.POST)
    public ActionResult Modify(@RequestBody ActivityDto input){
        Actor result=_actorService.Modify(new Actor());
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
}
