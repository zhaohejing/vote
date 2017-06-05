package com.efan.appservice.iservice;


import com.efan.controller.dtos.ActivityDto;
import com.efan.controller.inputs.BaseInput;
import com.efan.controller.inputs.DeleteInput;
import com.efan.core.page.ResultModel;
import com.efan.core.primary.Activity;

import java.util.List;

/**
 *接口列表
 */
public interface IActivityService {
    /*获取活动列表分页数据*/
     ResultModel<Activity> Activitys(BaseInput input);
    /*获取详情*/
     Activity Activity(DeleteInput input);
    /*删除*/
     void   Delete(DeleteInput input);
    /*创建或编辑*/
     Activity   Modify(ActivityDto input);
    List<Activity> AllActivitys();
    Activity Public(DeleteInput input);
}
