package com.efan.controller;


import com.efan.appservice.iservice.IGiftService;
import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.OrderInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Giving;
import com.efan.core.primary.Order;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单处理列表
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private IOrderService _orderService;
    private WxPayController _payController;
    private IGiftService _giftService;
    @Autowired
    public OrderController(IOrderService orderService,WxPayController payController,IGiftService giftService){
        _orderService=orderService;
        _payController=payController;
        _giftService=giftService;
    }

    /**
     * 获取我的支付订单带分页*/
    @ApiOperation(value="获取我的支付订单带分页", notes="订单接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "BaseInput")
    @RequestMapping(value  ="/myorders" ,method = RequestMethod.POST)
    public ActionResult MyOrders(@RequestBody BaseInput input){
        ResultModel<Order> result=_orderService.Orders(input);
        return  new ActionResult(result);
    }
    /**
     * 创建订单*/
    @ApiOperation(value="创建订单", notes="订单接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "OrderDto")
    @RequestMapping(value  ="/create" ,method = RequestMethod.POST)
    public ActionResult Create
    (@RequestBody OrderDto input){
        Order result=_orderService.CreatOrder(input);
        return  new ActionResult(result);
    }
    /**
     * 修改订单状态*/
    @ApiOperation(value="修改订单状态", notes="订单接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "OrderInput")
    @RequestMapping(value  ="/updateState" ,method = RequestMethod.POST)
    public ActionResult UpdateState(@RequestBody OrderInput input){
        try{
         Boolean state=   _payController.SearchOrder(input.orderNumber,input.price);
         if (state){
             Giving model= _giftService.SendGift(input);
            Order o= _orderService.UpdateState(input.orderNumber);
             return  new ActionResult(o);
         }else  {
             return new ActionResult(false,"微信订单支付失败,请重试");
         }

        }catch (Exception e){
            return new ActionResult(false,e.getMessage());
        }
    }


}