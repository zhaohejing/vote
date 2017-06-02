package com.efan.appservice.iservice;

import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Gift;

/**
 * Created by 45425 on 2017/6/2.
 */
public interface IGiftService {
    /*获取活动列表分页数据*/
     ResultModel<Gift> Gifts(BaseInput input);
    /*获取详情*/
     Gift Gift(DeleteInput input);
    /*删除*/
     void   Delete(DeleteInput input);
    /*创建或编辑*/
     Gift Modify(Gift input);

}
