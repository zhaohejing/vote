package com.efan.controller;


import com.efan.appservice.iservice.IGiftService;
import com.efan.controller.dtos.GiftDto;
import com.efan.controller.dtos.SendDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Gift;
import com.efan.core.primary.Giving;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 远程购买接口表
 */
@RestController
@RequestMapping("/api/gift")
public class GiftController {
    private IGiftService _giftService;
    @Autowired
    public GiftController(IGiftService giftService){
        _giftService=giftService;
    }


    /**
     * 获取礼物列表带分页*/
    @ApiOperation(value="获取礼物列表带分页", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/gifts" ,method = RequestMethod.POST)
    public ActionResult Gifts(@RequestBody BaseInput input){
        ResultModel<Gift> result=_giftService.Gifts(input);
        return  new ActionResult(result);
    }
    /**
     * 获取活动下礼物列表带分页*/
    @ApiOperation(value="获取活动下礼物列表带分页", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/giftsbyactivity" ,method = RequestMethod.POST)
    public ActionResult Gifts(@RequestBody DeleteInput input){
        try{
            ResultModel<Gift> result=_giftService.GiftsByActivityId(input);
            return  new ActionResult(result);
        }catch (Exception e){
            return  new ActionResult(false,e.getMessage());
        }

    }
    /**
     * 添加礼物*/
    @ApiOperation(value="添加礼物", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "GiftDto")
    @RequestMapping(value  ="/modify" ,method = RequestMethod.POST)
    public ActionResult Modify(@RequestBody GiftDto input){
        if (input.activityId==null||input.activityId<=0){
            return new ActionResult(false,"活动不存在,请先添加活动");
        }
        Gift result=_giftService.Modify(input);
        return  new ActionResult(result);
    }
    /**
     * 删除礼物*/
    @ApiOperation(value="删除礼物", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/delete" ,method = RequestMethod.POST)
    public ActionResult Delete(@RequestBody DeleteInput input){
        _giftService.Delete(input);
        return  new ActionResult(1);
    }
    /**
     * 获取礼物详情*/
    @ApiOperation(value="获取礼物详情", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/detail" ,method = RequestMethod.POST)
    public ActionResult Detail(@RequestBody DeleteInput input){
        Gift model=_giftService.Gift(input);
        return  new ActionResult(model);
    }
    /**
     * 送礼物*/
    @ApiOperation(value="送礼物", notes="礼物接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "SendDto")
    @RequestMapping(value  ="/send" ,method = RequestMethod.POST)
    public ActionResult Send(@RequestBody SendDto input){
        try {
            Giving model= _giftService.SendGift(input);
            return  new ActionResult(model);
        }catch (Exception e){
            return  new ActionResult(false,e.getMessage());
        }
    }
}
