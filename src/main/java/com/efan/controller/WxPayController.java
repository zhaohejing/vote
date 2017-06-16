package com.efan.controller;


import com.alibaba.fastjson.JSONObject;
import com.efan.appservice.iservice.IGiftService;
import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.DeleteInput;
import com.efan.controller.inputs.JsPayInput;
import com.efan.core.page.ActionResult;
import com.efan.core.primary.Gift;
import com.efan.repository.IGiftRepository;
import com.efan.utils.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/pay")
@EnableSwagger2
public class WxPayController {
   @Value("${wx.appId}")
    private String appId;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.saleId}")
    private String saleId;

    private IOrderService _orderService;
    private IGiftRepository _giftRepository;
    @Autowired
   public  WxPayController(IOrderService orderService,IGiftRepository giftRepository){
    _orderService=orderService;
    _giftRepository=giftRepository;
   }

    /**
     * 微信js预支付接口*/
    @ApiOperation(value="微信js预支付接口", notes="微信支付接口")
    @ApiImplicitParam(name = "input", value = "dto对象", required = true, dataType = "JsPayInput")
    @RequestMapping(value  ="/jspay" ,method = RequestMethod.POST)
    public ActionResult jspay
    (@RequestBody JsPayInput input){
        if (input.giftId==null||input.giftId<=0){
            return  new ActionResult(false,"礼物信息不存在");
        }
        try {
            Gift gift=_giftRepository.findOne(input.giftId);
            if (gift==null){
                return  new ActionResult(false,"礼物不存在");
            }
            //处理价格单位为：分(请自行处理)
            String  WIDtotal_fee= gift.getPrice().toString();
            String nom= Md5Utils.getUuid();
            String preid=getPrepayid(nom, WIDtotal_fee, input.openId,input.redirectUrl,input.userIp);//获取预支付标示
if (preid==null||preid.isEmpty()){
    return  new ActionResult(false,"生成预支付定单失败");
}
            OrderDto dto=new OrderDto(nom,input.openId,gift.getId(),gift.getGiftName(),gift.getPrice(),"");
            _orderService.CreatOrder(dto);
            //组装map用于生成sign
            Map<String, String> result=new HashMap<String, String>();
            result.put("appId", appId);
            //时间戳
            String timeStamp=(System.currentTimeMillis()/1000)+"";
            result.put("timeStamp", timeStamp);
            //随机字符串
            String nonceStr= RandomUtil.generateLowerString(16);
            result.put("nonceStr", nonceStr);
            //预支付标识
            result.put("prepay_id",preid);
            //金额
            result.put("total_fee",gift.getPrice().toString());

            //加密方式
            result.put("signType", "MD5");
            //组装map用于生成sign
            Map<String, String> map=new HashMap<String, String>();
            map.put("appId", appId);
            map.put("timeStamp", timeStamp);
            map.put("nonceStr", nonceStr);
            map.put("package", "prepay_id="+preid);
            map.put("signType", "MD5");
            ;
            result.put("paySign", Md5Utils.sign(map,"1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y").toUpperCase());//签名
            result.put("order", nom);
            return new ActionResult(result);
        }catch (Exception e){
            return  new ActionResult(false,e.getMessage());
        }
    }
    /**
     * 为新订单查询接口*/
    @ApiOperation(value="为新订单查询接口", notes="微信支付接口")
    @RequestMapping(value  ="/search" ,method = RequestMethod.POST)
        public  ActionResult FindOrder(String out_trade_no,Integer price){
        ActionResult result ;
    String url="https://api.mch.weixin.qq.com/pay/orderquery";
    String nonce_str = RandomUtil.generateLowerString(16);//生成随机数，可直接用系统提供的方法
    String body = "vote-商品订单";
    HashMap<String, String> map = new HashMap<String, String>();
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

        result=new ActionResult(jsonObject);
    }catch (Exception e){
        result=new ActionResult(false,e.getMessage());
    }
return  result;
}
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
        map.put("attach", "test");
        map.put("device_info", "WEB");
        map.put("nonce_str", nonce_str);
        String tempBody= getUTF8XMLString(body);
        map.put("body",tempBody);
        map.put("out_trade_no", out_trade_no);
        map.put("total_fee", total_fee);
        map.put("spbill_create_ip", spbill_create_ip);
        map.put("trade_type", trade_type);
        map.put("notify_url", notify_url);
        map.put("openid", openid);
        String sign = Md5Utils.sign(map,"1q2w3e4r5t6y7u8i9o0p1q2w3e4r5t6y").toUpperCase();//参数加密
      //  System.out.println("sign秘钥:-----------"+sign);
        map.put("sign", sign);
        //组装xml(wx就这么变态，非得加点xml在里面)
        String content= Md5Utils.MapToXmlNoReg(map);
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
    public  String getUTF8XMLString(String text) {
        // A StringBuffer Object
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
    @RequestMapping(value = "notify" ,method = RequestMethod.GET)
    @ResponseBody
    public void notify(HttpServletRequest request,HttpServletResponse response) throws Exception {
        System.out.println("----接收到的数据如下：---" + request);
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //------------------------------
        //处理业务开始
        //------------------------------
        String resXml = "";
        if (sb.toString().isEmpty()) {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }else   {
            //解析xml成map
            JSONObject m;
            m = XmlJsonUtil.xml2Json(sb.toString());
            if ("SUCCESS".equals(m.getString("result_code"))) {
                String out_trade_no =  m.getString("out_trade_no");
                _orderService.UpdateState(out_trade_no);
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            } else {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
        }

        //------------------------------
        //处理业务完毕
        //------------------------------
        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }
}


