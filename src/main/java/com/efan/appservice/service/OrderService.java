package com.efan.appservice.service;

import com.efan.appservice.iservice.IOrderService;

import com.efan.core.page.Response;
import com.efan.repository.primary.IOrderRepository;
import com.efan.utils.HttpUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 购买订单相关接口
 */
@Service
public class OrderService implements IOrderService {
    @Value("${efanurl}")
    private String efanurl;
    @Value("${returnurl}")
    private String returnurl;
    private IOrderRepository _orderRepository;
    @Autowired
     public OrderService(IOrderRepository orderRepository){
         this._orderRepository=orderRepository;
     }

    /**
     * 获取包厢列表
     * */
    public Response GetCoupeList(String remoteId)  {
        String url=efanurl+"api/getMachineListBySpot";
        String parms="spot_id="+remoteId;
        String result=  HttpUtils.sendPost(url,parms);
        Response res;
        try{
            res =   new Gson().fromJson(result,Response.class);
        }catch (Exception e){
           res=new Response();
           res.code=1000;
           res.message=result;
        }
        return  res;
    }

    private  Date GenderTime(Date time,Boolean isstart){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        if (isstart){
            calendar.set(Calendar.HOUR,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        }else   {
            calendar.set(Calendar.HOUR,23);
            calendar.set(Calendar.MINUTE,59);
            calendar.set(Calendar.SECOND,59);
            calendar.set(Calendar.MILLISECOND,999);
        }
        return  calendar.getTime();
    }
    private  String getTimeDifference(Timestamp a, Timestamp b) {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
        long t2 = 0L;
        try {
            t1 = timeformat.parse(getTimeStampNumberFormat(a)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            t2 = timeformat.parse(getTimeStampNumberFormat(b)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
        int hours=(int) ((t1 - t2)/3600000);
        int minutes=(int) (((t1 - t2)/1000-hours*3600)/60);
        int second=(int) ((t1 - t2)/1000-hours*3600-minutes*60);
        return ""+  hours*60+minutes+(second/60);
    }
    private  String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
}
