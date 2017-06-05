package com.efan.controller.inputs;

/**
 * 蚌明着dto
 */
public class ActorInput extends BaseInput {
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    private  Long activityId;


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    private  String sort;
}
