package com.efan.core.primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



/**
 * 奖品实体
 */
@Entity
@Table(name="prize")
public class Prize implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //活动id
    @NotNull
    private Long activityId;
    //奖品等级
    @NotNull
    private Integer level;
    @NotNull
    //"礼物名称")
    @Column(length = 120,unique = false)
    private  String prizeName;
    @NotNull
    //"奖品图片名称")
    @Column(columnDefinition = "text")
    private   String imageName;
    @NotNull
    //"奖品图片路径")
    @Column(columnDefinition = "text")
    private  String imageUrl;
    //描述
    private  String description;
    //"创建时间")
    @Column(length = 50)
    private String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }
}
