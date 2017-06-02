package com.efan.controller;


import com.efan.appservice.iservice.IActivityService;
import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 远程购买接口表
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {
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
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/delete" ,method = RequestMethod.POST)
    public ActionResult Delete(@RequestBody DeleteInput input){
       _activityService.Delete(input);
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
}
