package com.efan.controller.inputs;

/**
 * Created by 45425 on 2017/6/2.
 */
public class ActorInput extends BaseInput {
    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    private  Long activityId;
}
