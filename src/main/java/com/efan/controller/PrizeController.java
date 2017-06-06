package com.efan.controller;

import com.efan.appservice.iservice.IPrizeService;
import com.efan.controller.dtos.PrizeDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Prize;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 45425 on 2017/6/6.
 */
@RestController
@RequestMapping("/api/prize")
public class PrizeController {
    private IPrizeService _prizeService;
    @Autowired
    public PrizeController(IPrizeService prizeService){
        _prizeService=prizeService;
    }

    /**
     * 获取奖品列表带分页*/
    @ApiOperation(value="获取礼物列表带分页", notes="奖品接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/prizes" ,method = RequestMethod.POST)
    public ActionResult Prizes(@RequestBody BaseInput input){
        ResultModel<Prize> result=_prizeService.Prizes(input);
        return  new ActionResult(result);
    }
    /**
     * 添加奖品*/
    @ApiOperation(value="添加奖品", notes="奖品接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "PrizeDto")
    @RequestMapping(value  ="/modify" ,method = RequestMethod.POST)
    public ActionResult Modify(@RequestBody PrizeDto input){
        if (input.activityId==null||input.activityId<=0){
            return new ActionResult(false,"活动不存在,请先添加活动");
        }
        Prize result=_prizeService.Modify(input);
        return  new ActionResult(result);
    }
    /**
     * 删除奖品*/
    @ApiOperation(value="删除奖品", notes="奖品接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/delete" ,method = RequestMethod.POST)
    public ActionResult Delete(@RequestBody DeleteInput input){
        _prizeService.Delete(input);
        return  new ActionResult(1);
    }
    /**
     * 获取奖品详情*/
    @ApiOperation(value="获取奖品详情", notes="奖品接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/detail" ,method = RequestMethod.POST)
    public ActionResult Detail(@RequestBody DeleteInput input){
        Prize model=_prizeService.Prize(input);
        return  new ActionResult(model);
    }
}
