package com.efan.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.efan.appservice.iservice.IGiftService;
import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.DeleteInput;
import com.efan.controller.inputs.JsPayInput;
import com.efan.core.page.ActionResult;
import com.efan.core.primary.Gift;
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

@RestController
@RequestMapping("/api/prize")
public class WxPayController {
    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;
    @Value("${saleId}")
    private String saleId;

    private IGiftService _giftService;
    private IOrderService _orderService;
    @Autowired
   public  WxPayController(IGiftService giftService,IOrderService orderService){
    _giftService=giftService;
    _orderService=orderService;
   }

    /**
     * 微信js预支付接口*/
    @ApiOperation(value="微信js预支付接口", notes="微信支付接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "JsPayInput")
    @RequestMapping(value  ="/jspay" ,method = RequestMethod.POST)
    public ActionResult jspay
    (@RequestBody JsPayInput input){
        try {
            Gift gift=_giftService.Gift(new DeleteInput(input.giftId));
            if (gift==null){
                return  new ActionResult(false,"礼物不存在");
            }
            //处理价格单位为：分(请自行处理)
            String  WIDtotal_fee= gift.getPrice().toString();
            String nom=Md5Utils.getUuid();
            String preid=getPrepayid(nom, WIDtotal_fee, input.openId,input.redirectUrl,input.userIp);//获取预支付标示

            OrderDto dto=new OrderDto(nom,input.openId,gift.getId(),gift.getGiftName(),gift.getPrice(),"");
            _orderService.CreatOrder(dto);
            //组装map用于生成sign
            Map<String, String> result=new HashMap<String, String>();
            result.put("appId", appId);
            //时间戳
            String timeStamp=(System.currentTimeMillis()/1000)+"";
            result.put("timeStamp", timeStamp);
            //随机字符串
            String nonceStr=RandomUtil.generateLowerString(16);
            result.put("nonceStr", nonceStr);
            //预支付标识
            result.put("prepay_id",preid);
            //加密方式
            result.put("signType", "MD5");
            //组装map用于生成sign
            Map<String, String> map=new HashMap<String, String>();
            map.put("appId", appId);
            map.put("timeStamp", timeStamp);
            map.put("nonceStr", nonceStr);
            map.put("package", "prepay_id="+preid);
            map.put("signType", "MD5");

            result.put("paySign", Md5Utils.sign(map,"vote"));//签名
            return new ActionResult(result);
        }catch (Exception e){
            return  new ActionResult(false,e.getMessage());
        }
    }

    /**
     * 微信统一下单接口,获取预支付标示prepay_id
     * @param out_trade_no1 商户订单号
     * @param total_fee1 订单金额(单位:分)
     * @param openid1 网页授权取到的openid

     */

    private String getPrepayid(String out_trade_no1,String total_fee1,String openid1,String redirt,String userIp) throws  Exception{
        String result = "";
        String appid = appId;
        String mch_id = saleId;
        String nonce_str = RandomUtil.generateLowerString(16);//生成随机数，可直接用系统提供的方法
        String body = "vote-商品订单";
        String out_trade_no = out_trade_no1;
        String total_fee = total_fee1;
        String spbill_create_ip = userIp;//用户端ip,这里随意输入的
        String notify_url = redirt;//支付回调地址
        String trade_type = "JSAPI";
        String openid = openid1;

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("attach", "测试支付");
        map.put("device_info", "WEB");
        map.put("nonce_str", nonce_str);
        map.put("body", body);
        map.put("out_trade_no", out_trade_no);
        map.put("total_fee", total_fee);
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("trade_type", trade_type);
        map.put("notify_url", notify_url);
        map.put("openid", openid);
        String sign = Md5Utils.sign(map,"vote");//参数加密
        System.out.println("sign秘钥:-----------"+sign);
        map.put("sign", sign);
        //组装xml(wx就这么变态，非得加点xml在里面)
        String content= Md5Utils.MapToXml(map);
        //System.out.println(content);
        String PostResult= HttpUtils.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", content);
        try{
            JSONObject jsonObject= XmlJsonUtil.xml2Json(PostResult);//返回的的结果
            if(jsonObject.getString("return_code").equals("SUCCESS")&&jsonObject.getString("result_code").equals("SUCCESS")){
                result=jsonObject.get("prepay_id")+"";//这就是预支付id
            }
            return result;
        }catch (Exception e){
            return  e.getMessage();
        }

    }

}


