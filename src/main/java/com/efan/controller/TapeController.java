package com.efan.controller;

import com.efan.appservice.iservice.IMyTapeService;
import com.efan.controller.dtos.MyTapeDto;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.primary.MyTape;
import com.efan.core.page.ActionResult;
import com.efan.core.page.FilterModel;
import com.efan.core.page.ResultModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 录音接口列表
 */
@RestController
@RequestMapping("/api/tape")
public class TapeController {
    private IMyTapeService _mytapeService;
    @Autowired
    public TapeController(IMyTapeService mytapeService){
this._mytapeService=mytapeService;
    }
    /*创建我的原唱歌曲*/
    @ApiOperation(value="创建我的原唱歌曲", notes="我的模块接口")
    @RequestMapping(value  ="/insertTape" ,method = RequestMethod.POST)
    public ActionResult InsertTape(@RequestBody MyTapeDto input){
        MyTape result=_mytapeService.InsertMyTape(input);
        return  new ActionResult(result);
    }
    /*设备上传文件 获取到qiniu的路径 保存*/
    @ApiOperation(value="设备上传文件 获取到qiniu的路径 保存", notes="我的模块接口")
    @RequestMapping(value  ="/notifyupload" ,method = RequestMethod.POST)
    public ActionResult NotifyUploadSongs(@RequestBody MyTapeDto input){
        MyTape result=_mytapeService.ModifyMyTape(input);
        return  new ActionResult(result);
    }
    /*修改我的歌单文件上传状态*/
    @ApiOperation(value="修改我的歌单文件上传状态", notes="我的模块接口")
    @RequestMapping(value  ="/updatemytapestate" ,method = RequestMethod.POST)
    public ActionResult UpdateMyTapeState(@RequestParam Long id){
        MyTape result=_mytapeService.UpdateMyTapeState(id);
        return  new ActionResult(result);
    }
    /*获取我的录音列表*/
    @ApiOperation(value="获取我的以保留录音列表", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 }", required = true, dataType = "FilterModel")
    @RequestMapping(value  ="/mytapes" ,method = RequestMethod.POST)
    public ActionResult MyTapes(@RequestBody FilterModel input){
        ResultModel<MyTape> result=_mytapeService.GetMyTapeList(input);
        return  new ActionResult(result);
    }
    /*获取我的全部录音列表*/
    @ApiOperation(value="获取我的全部录音列表", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "{filter:过滤条件,index:页码,size:页容量 }", required = true, dataType = "FilterModel")
    @RequestMapping(value  ="/myalltapes" ,method = RequestMethod.POST)
    public ActionResult MyAllTapes(@RequestBody FilterModel input){
        ResultModel<MyTape> result=_mytapeService.GetAllMyTapeList(input);
        return  new ActionResult(result);
    }
    /*获取我的录音详情*/
    @ApiOperation(value="获取我的录音详情", notes="我的模块接口")
    @ApiImplicitParam(name = "input", value = "{id }", required = true, dataType = "DeleteInput")
    @RequestMapping(value  ="/mytape" ,method = RequestMethod.POST)
    public ActionResult MyTapes(@RequestBody DeleteInput input){
        MyTape result=_mytapeService.GetMyTape(input);
        return  new ActionResult(result);
    }
}
