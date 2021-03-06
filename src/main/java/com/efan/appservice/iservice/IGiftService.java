package com.efan.appservice.iservice;

import com.efan.controller.dtos.GiftDto;
import com.efan.controller.dtos.SendDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Gift;
import com.efan.core.primary.Giving;

/**
 * 礼物接口
 */
public interface IGiftService {
    /*获取活动列表分页数据*/
     ResultModel<Gift> Gifts(BaseInput input);
    /*获取详情*/
     Gift Gift(DeleteInput input);
    /*删除*/
     void   Delete(DeleteInput input);
    /*创建或编辑*/
     Gift Modify(GiftDto input);
    //送礼物
    Giving SendGift(SendDto dto) throws Exception;
    ResultModel<Gift> GiftsByActivityId(DeleteInput input) throws Exception;
    Giving SendGiftByScheduled(SendDto dto) throws Exception;
}
