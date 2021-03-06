package com.efan.controller;


import com.efan.appservice.iservice.IActivityService;
import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.OutPuts.ActivityOutPut;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.controller.inputs.ListInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 活动列表
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController extends BaseController {
    private IActivityService _activityService;

    @Autowired
    public ActivityController(IActivityService activityService){
        _activityService=activityService;
    }
/**
 * 获取活动列表带分页*/
    @ApiOperation(value="获取活动列表带分页", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/activitys" ,method = RequestMethod.POST)
    public ActionResult Activitys(@RequestBody BaseInput input){
        ResultModel<Activity> result=_activityService.Activitys(input);
            return  new ActionResult(result);
    }
    /**
     * 获取所有已创建的活动*/
    @ApiOperation(value="获取所有已创建的活动", notes="后台活动接口")
    @RequestMapping(value  ="/allactivitys" ,method = RequestMethod.POST)
    public ActionResult AllActivitys(){
        List<Activity> result=_activityService.AllActivitys();
        return  new ActionResult(result);
    }
    /**
     * 添加或编辑活动*/
    @ApiOperation(value="添加投票活动", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ActivityDto")
    @RequestMapping(value  ="/modify" ,method = RequestMethod.POST)
    public ActionResult Modify(@RequestBody ActivityDto input){
      Activity result=_activityService.Modify(input);
      return  new ActionResult(result);
    }
    /**
     * 删除活动*/
    @ApiOperation(value="删除活动", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ListInput")
    @RequestMapping(value  ="/delete" ,method = RequestMethod.POST)
    public ActionResult Delete(@RequestBody ListInput input){
       _activityService.Delete(input);
       return  new ActionResult(1);
    }
    /**
     * 活动状态发布*/
    @ApiOperation(value="活动状态发布", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "ListInput")
    @RequestMapping(value  ="/public" ,method = RequestMethod.POST)
    public ActionResult Public(@RequestBody ListInput input){
       _activityService.Public(input);
        return  new ActionResult(1);
    }
    /**
     * 获取活动详情*/
    @ApiOperation(value="获取活动详情", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/detail" ,method = RequestMethod.POST)
    public ActionResult Detail(@RequestBody DeleteInput input){
        Activity model=_activityService.Activity(input);
        return  new ActionResult(model);
    }
    /**
     * 获取活动详情*/
    @ApiOperation(value="获取活动详情", notes="后台活动接口")
    @RequestMapping(value  ="/getdetail" ,method = RequestMethod.GET)
    public ActionResult GetDetail(@RequestParam Long activityId){
        try {
            ActivityOutPut model=_activityService.GetDetail(activityId);
            return  new ActionResult(model);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return  new ActionResult(false, e.getMessage());
        }
    }
    /**
     * 增加访问量*/
    @ApiOperation(value="增加访问量", notes="后台活动接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/access" ,method = RequestMethod.POST)
    public ActionResult Access(@RequestBody DeleteInput input){
        try{
            Activity model=_activityService.Access(input);
            return  new ActionResult(model);
        }catch (Exception e){
            logger.error(e.getMessage());
            return  new ActionResult(false,e.getMessage());
        }

    }
}
