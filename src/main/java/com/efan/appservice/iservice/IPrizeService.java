package com.efan.appservice.iservice;

import com.efan.controller.dtos.PrizeDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Prize;

/**
 * Created by 45425 on 2017/6/6.
 */
public interface IPrizeService {
    /*获取活动列表分页数据*/
     ResultModel<Prize> Prizes(BaseInput input);
    /*获取详情*/
     Prize Prize(DeleteInput input);
    /*删除*/
     void   Delete(DeleteInput input);
    /*创建或编辑*/
     Prize   Modify(PrizeDto input);

}
