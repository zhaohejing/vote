package com.efan.controller;

import com.efan.core.page.ActionResult;
import com.efan.utils.HttpUtils;
import com.efan.utils.TokenSingleton;
import com.google.gson.Gson;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;


@RestController
@RequestMapping("/api/token")
@EnableSwagger2
public class TokenController {
    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @ApiOperation(value="获取七牛token", notes="七牛接口")
    @RequestMapping(value = "/qnToken", method = RequestMethod.POST)
    public ModelMap token(){
        String bucket = "resource";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        ModelMap result = new ModelMap();
        if(upToken != null){
            result.put("result","1");
            result.put("errorMsg","获取成功");
            result.put("data",upToken);
        }else {
            result.put("result", "0");
            result.put("errorMsg", "获取失败");
        }
        return result;
    }


    @ApiOperation(value="获取微信access_token", notes="微信接口")
    @RequestMapping(value = "/getWxAccessToken", method = RequestMethod.POST)
    public ActionResult getWxAccessToken(@RequestParam String code){
       Map token=  getAccessToken(code);
       return  new ActionResult(token);
    }

    @ApiOperation(value="获取微信signature", notes="微信接口")
    @RequestMapping(value = "/getWxSignature", method = RequestMethod.POST)
    public ActionResult getWxSignature(@RequestParam String noncestr,@RequestParam String timestamp,@RequestParam String url){
        String token=getToken();
        if (token.isEmpty()){
            return  new ActionResult(false,"请先获取token");
        }
        String ticket=getTicket(token);
        if (    ticket.isEmpty()){
            return  new ActionResult(false,"请先获取ticket");

        }
        String signNature=  getSignature(noncestr,timestamp,url,ticket);
        return  new ActionResult(signNature);
    }

    @ApiOperation(value="获取用户基本信息", notes="微信接口")
    @RequestMapping(value = "/getuserinfo", method = RequestMethod.POST)
    public String getUserInfo(@RequestParam String token, @RequestParam String openId){
      /*  if (token.isEmpty()){
        }
        Map map = getAccessToken(code);
        String access_token = (String) map.get("access_token");*/
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+token+"&openid="+openId+"&lang=zh_CN";
        String result = HttpUtils.sendPost(url,"");
        return result;
    }
     /*获取token*/
     private  String getToken(){
        if (TokenSingleton.getInstance().getWxToken() != null &&TokenSingleton.getInstance().getTokenTime()>System.currentTimeMillis()){
            return TokenSingleton.getInstance().getWxToken();
        }

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
        String result = HttpUtils.sendPost(url,"");

        Gson gson = new Gson();

        Map map = gson.fromJson(result,Map.class);

        String access_token = (String) map.get("access_token");
         Double error = (Double) map.get("errcode");

         if (error!=null&& error>0){
             String message=(String)map.get("errmsg");
           //  TokenSingleton.getInstance().setWxToken("");
             return "";
         }
        Double expires_in = (Double) map.get("expires_in");
        //获取当前时间戳
        long sjc = System.currentTimeMillis();
        //设置token
        TokenSingleton.getInstance().setWxToken(access_token);
        //设置token过期时间
        TokenSingleton.getInstance().setTokenTime(sjc + expires_in.longValue()*1000);
        return access_token;
    }
    /*获取access_token*/
    private  Map getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpUtils.sendPost(url,"");
        Gson gson = new Gson();
        Map map = gson.fromJson(result,Map.class);
        String access_token = (String) map.get("access_token");
        String openId = (String) map.get("openid");

        Double error = (Double) map.get("errcode");
        if (error!=null&& error>0){
            String message=(String)map.get("errmsg");
            //  TokenSingleton.getInstance().setWxToken("");
            return null ;
        }
        return map;
    }
    private  String getTicket(String token){
        if (TokenSingleton.getInstance().getTicket() != null &&TokenSingleton.getInstance().getTicketTime()>System.currentTimeMillis()){
            return TokenSingleton.getInstance().getTicket();
        }

        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+token+"&type=jsapi";
        String result = HttpUtils.sendPost(url,"");

        Gson gson = new Gson();

        Map map = gson.fromJson(result,Map.class);

        String ticket = (String) map.get("ticket");
        Double error = (Double) map.get("errcode");

        if (error!=null&&error>0){
          //  String message=(String)map.get("errmsg");
              TokenSingleton.getInstance().setWxToken("");
              return "";
         }

        Double expires_in = (Double) map.get("expires_in");
        //获取当前时间戳
        long sjc = System.currentTimeMillis();
        //设置token
        TokenSingleton.getInstance().setTicket(ticket);
        //设置token过期时间
        TokenSingleton.getInstance().setTicketTime(sjc + expires_in.longValue()*1000);
        return ticket;
    }
    private   String getSignature(String noncestr,String timestamp,String url,String ticket ){

        String str = "jsapi_ticket=" + ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //sha1加密
        String signature =HttpUtils.SHA1(str);
    return  signature;

    }
}
