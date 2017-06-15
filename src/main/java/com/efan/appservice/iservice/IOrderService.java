package com.efan.appservice.iservice;

import com.efan.controller.dtos.OrderDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.OrderInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Order;

/**
 * Created by 45425 on 2017/6/8.
 */
public interface IOrderService {
    /*获取我的订单分页数据*/
     ResultModel<Order> Orders(BaseInput input);

    /*创建或编辑*/
     Order   CreatOrder(OrderDto input);
    Order UpdateState(String orderNum);
}
