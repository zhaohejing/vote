package com.efan.core.primary;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 图片表
 */
@Entity
@Table(name="image")
public class Image implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //骑牛url
    @NotNull
    @Column(length = 50)
     //"骑牛图片路径")
    private String url;
    //骑牛url
    @NotNull
    @Column(length = 50)
    //"骑牛图片路径")
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @NotNull
     //"活动id")
    private  Long activityId;
    //是否已上传
    private  Boolean  state;
    private String creationTime;
}
