package com.efan.core.primary;

import com.sun.org.glassfish.gmbal.Description;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 参与者
 */
@Entity
@Table(name="actor")
public class Actor implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //用户唯一id
    @NotNull
    @Description("用户唯一id key")
    @Column(length = 50)
    private String actorKey;

    @Column(length = 50)
    @Description("用户名 ")
    private  String  actorName;

    @Column(length = 50)
    @Description("用户头像路径 ")
    private  String  actorImage;
    @Description("投票数 ")
    private  Integer actorCount;
    //用户唯一id
    @Description("用户唯一id key")
    @Column(length = 50)
    private String creationTime;
   private  Long activityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActorKey() {
        return actorKey;
    }

    public void setActorKey(String actorKey) {
        this.actorKey = actorKey;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorImage() {
        return actorImage;
    }

    public void setActorImage(String actorImage) {
        this.actorImage = actorImage;
    }

    public Integer getActorCount() {
        return actorCount;
    }

    public void setActorCount(Integer actorCount) {
        this.actorCount = actorCount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
