package com.efan.appservice.service;


import com.efan.appservice.iservice.IOrderService;
import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.OrderInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Order;
import com.efan.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *礼物创建相关
 */
@Service
public class OrderService implements IOrderService {

    private IOrderRepository _orderRepository;


    @Autowired
    public OrderService(IOrderRepository orderRepository){
        this._orderRepository=orderRepository;

    }
    /*获取我的订单分页数据*/
    public ResultModel<Order> Orders(BaseInput input){
        //  Sort sort = new Sort(Sort.Direction.DESC, "createdate");
        Pageable pageable = new PageRequest(input.getIndex()-1, input.getSize(),null);
        Page<Order> res=  _orderRepository.findAllByPayKey(input.getFilter(), pageable);
        return  new ResultModel<>(res.getContent(),res.getTotalElements());
    }

    /*创建或编辑*/
    public Order   CreatOrder(OrderDto input){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Order model =new Order();
            model.setPrice(input.price);
            model.setCreationTime(df.format(new Date()));
            model.setId(0L);
            model.setOrderDes(input.orderDes);
            model.setOrderNumber(input.orderNum);
            model.setPayKey(input.payKey);
            model.setProductId(input.productId);
            model.setProductName(input.productName);
            model.setActorId(input.actorId);
            model.setPayImage(input.payImage);
            model.setPayName(input.payName);
            model.setActivityId(input.activityId);
            model.setPayState(false);
            model=  _orderRepository.save(model);
            return  model;
    }
    public Order UpdateState(String orderNum) {
            Order  order = _orderRepository.findByOrderNumber(orderNum);
        /*    if ( !order.getPrice().equals(input.price)){
                throw new Exception("价格信息错误,请重试");
            }*/
            order.setPayState(true);
          return   _orderRepository.saveAndFlush(order);
    }

}