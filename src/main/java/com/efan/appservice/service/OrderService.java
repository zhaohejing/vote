package com.efan.appservice.service;

import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderTime;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.OrderInput;
import com.efan.controller.inputs.RemoteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.MyTape;
import com.efan.core.primary.Order;
import com.efan.core.page.Response;
import com.efan.repository.primary.IOrderRepository;
import com.efan.utils.HttpUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
      * 获取门店列表
     * */
    public Response GetRemoteList(RemoteInput input) {
        String url=efanurl+"api/getSpotsByCoordinate";
        String parms="longitude="+input.x+"&latitude"+input.y+"&page="+input.page;
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


    /*获取预定订单列表*/
    public List<OrderTime> GetOrderList(String boxId, Date date){
        Date start=GenderTime(date,true);
        Date end=GenderTime(date,false);
        List<OrderTime> result=new ArrayList<>();
          List<Order> list= _orderRepository.findOrders(boxId,start,end);
        for (int i = 0; i <24 ; i++) {
            Integer count=0;
            for (int j = 0; j < list.size(); j++) {
                Order temp=list.get(j);
               Timestamp from = temp.getFromTime();
                Integer hour=  from.getHours();
                Integer min=  from.getMinutes();
                Timestamp to = temp.getToTime();
                Integer tohour=  to.getHours();
                Integer tomin=  to.getMinutes();
                if (i==hour){
                    if (i==tohour){
                        count+=(tomin-min);
                    }else {
                        count+=(60-min);
                    }
                    }
                    if (i==tohour){
                        if (hour<tohour){
                            count+=tomin;
                        }
                    }
            }
            result.add(new OrderTime(i,i+1,count));
        }
        return  result;
    }
///根据lexington获取套餐详情
     public  Response GetOrderTypeList(Boolean isRemote,String boxId){
         String url=efanurl+"api/getProductsByRoom";
         String parms="room_id="+boxId+"&isremote="+isRemote;
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
     ///获取订单详情
     public  Order GetOrderDetail(String orderId){
     return   _orderRepository.findOrderByFilter(orderId);
     }
    public ResultModel<Order> GetMyOrders(BaseInput input){
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Order> res=  _orderRepository.findAllByUserKey(input.getFilter(), pageable);
        return  new ResultModel<Order>( res.getContent(),res.getTotalElements());

    }
//创建订单并调用支付接口
  //  @Async
    public Order CreateOrder(OrderInput input)  {
        Timestamp date = new Timestamp(System.currentTimeMillis());
      UUID num=   java.util.UUID.randomUUID();
      String pars=getTimeDifference(input.fromTime,input.toTime);
        Order model=new Order();
        model.setAmount(input.amount);
        model.setBoxId(input.boxId);
        model.setBoxName(input.boxName);
        model.setOrderNum(num.toString());
        model.setCommon(false);
        model.setPointName(input.pointName);
        model.setUserKey(input.userKey);
        model.setState(0);
        model.setPurchaseTime(pars);
        model.setModifyUserId(1L);
        model.setConsumerName(input.consumerName);
        model.setCreationTime(date);
        model.setOrderId(input.orderId);
        model.setCreationUserId(1L);
        model.setDelete(false);
        model.setFromTime(input.fromTime);
        model.setModifyTime(date);
        model.setMobile(input.consumerName);
        model.setOrderType(input.orderType);
        model.setToTime(input.toTime);
      return  _orderRepository.save(model);
    }

    public String Payfor(String boxId,String orderId){
        //调用微信支付
        String url="http://wxpay.dev.efanyun.com/order";
        String parms="?machineCode="+boxId+"&productId="+orderId+"&notifyUrl="+returnurl;
      return   HttpUtils.sendGet(url+parms);
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
