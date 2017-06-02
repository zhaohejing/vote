package com.efan.controller;


import com.efan.appservice.iservice.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 远程购买接口表
 */
@RestController
@RequestMapping("/api/order")
public class ActivityController {
    private IActivityService _activityService;
    @Autowired
    public ActivityController(IActivityService activityService){
        _activityService=activityService;
    }

  /*  *//**
     * 通知机器是否可以开唱*//*
    @ApiOperation(value="通知机器是否可以开唱", notes="远程购买接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "OrderDetailInput")
    @RequestMapping(value  ="/cansingit" ,method = RequestMethod.POST)
    public  ActionResult CanSingIt(@RequestBody OrderDetailInput input){
      Order model   =_orderService.GetOrderDetail(input.orderId);
      if ( model.getState()!=1){
          return  new ActionResult(false,null ,"订单还未支付"   );
      }
        return  new ActionResult(true,model.getOrderNum(),"获取成功,可以开唱");
    }
*/
}
