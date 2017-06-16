package com.efan.controller;


import com.alibaba.fastjson.JSONObject;
import com.efan.appservice.iservice.IGiftService;
import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.OrderInput;
import com.efan.core.page.ActionResult;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Giving;
import com.efan.core.primary.Order;
import com.efan.utils.HttpUtils;
import com.efan.utils.Md5Utils;
import com.efan.utils.RandomUtil;
import com.efan.utils.XmlJsonUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 订单处理列表
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.saleId}")
    private String saleId;
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
            if (input.activityId==null||input.activityId<=0){
                return new ActionResult(false,"活动不存在");
            }
      //   Boolean state=   SearchOrder(input.orderNumber,input.price);
         if (!input.orderNumber.isEmpty()){
             Giving model= _giftService.SendGift(input);
            Order o= _orderService.UpdateState(input.orderNumber);
             return  new ActionResult(model);
         }else  {
             return new ActionResult(false,"微信订单支付失败,请重试");
         }
        }catch (Exception e){
            return new ActionResult(false,e.getMessage());
        }
    }
    public    Boolean SearchOrder(String out_trade_no,Integer price){
        Boolean result = false;
        String url="https://api.mch.weixin.qq.com/pay/orderquery";
        String nonce_str = RandomUtil.generateLowerString(16);//生成随机数，可直接用系统提供的方法
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appId);
        map.put("mch_id", saleId);
        map.put("nonce_str", nonce_str);
        map.put("out_trade_no", out_trade_no);
        String sign = Md5Utils.sign(map,"1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y").toUpperCase();//参数加密
        map.put("sign", sign);
        //组装xml(wx就这么变态，非得加点xml在里面)
        String content= Md5Utils.MapToXmlNoReg(map);
        //System.out.println(content);
        String PostResult= HttpUtils.sendPost(url, content);
        try{
            JSONObject jsonObject= XmlJsonUtil.xml2Json(PostResult);//返回的的结果
            if(jsonObject.getString("return_code").equals("SUCCESS")&&
                    jsonObject.getString("result_code").equals("SUCCESS")){
                result=jsonObject.getString("trade_state").equals("SUCCESS") ;//这就是预支付id
            }
            return result;
        }catch (Exception e){
        }
        return  result;
    }

}