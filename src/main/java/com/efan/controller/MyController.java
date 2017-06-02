package com.efan.controller;

import com.efan.appservice.iservice.IMyTapeService;
import com.efan.appservice.iservice.IOrderService;
import com.efan.appservice.iservice.ITapeService;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.controller.inputs.MySongsInput;
import com.efan.controller.inputs.OrderDetailInput;
import com.efan.core.primary.MySongs;
import com.efan.core.page.ActionResult;
import com.efan.core.page.FilterModel;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Order;
import com.sun.xml.internal.rngom.parse.host.Base;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 我的接口列表
 */
@RestController
@RequestMapping("/api/my")
public class MyController {
    private IMyTapeService _mytapeService;
    private ITapeService _tapeService;
    private IOrderService _orderService;
    @Autowired
    public MyController(IMyTapeService mytapeService,ITapeService tapeService,IOrderService orderService){
        this._mytapeService=mytapeService;
        _tapeService=tapeService;
        this._orderService=orderService;

    }
    /*获取我的歌单列表*/
    @ApiOperation(value="获取我的歌单列表", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 }", required = true, dataType = "FilterModel")
    @RequestMapping(value  ="/mysongs" ,method = RequestMethod.POST)
    public ActionResult MySongs(@RequestBody FilterModel input){
        ResultModel<MySongs> result=_tapeService.GetMySongsList(input);
        return  new ActionResult(result);
    }
    /*创建我点过的歌曲列表*/
    @ApiOperation(value="创建我点过的歌曲列表", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "mysongsinput", required = true, dataType = "MySongsInput")
    @RequestMapping(value  ="/picksongs" ,method = RequestMethod.POST)
    public ActionResult PickSongs(@RequestBody MySongsInput input){
        MySongs result=_tapeService.CreateMySongs(input);
        return  new ActionResult(result);
    }
    /*取消已点*/
    @ApiOperation(value="取消已点歌曲", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "input", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/unpicksongs" ,method = RequestMethod.POST)
    public ActionResult UnPickSongs(@RequestBody DeleteInput input){
         _tapeService.DeleteMySongs(input.id);
        return  new ActionResult(1);
    }
    /**
     * 获取我的所有订单*/
    @ApiOperation(value="获取我的所有订单", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "{分页 filter 传用户唯一id}", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/myorders" ,method = RequestMethod.POST)
    public  ActionResult GetMyOrders(@RequestBody BaseInput input){
        ResultModel<Order> result   =_orderService.GetMyOrders(input);
        return  new ActionResult(result);
    }
}
