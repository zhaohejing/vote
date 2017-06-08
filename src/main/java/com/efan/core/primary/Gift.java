package com.efan.core.primary;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * 礼物实体
 */
@Entity
@Table(name="gift")
public class Gift implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
    //活动id
    @NotNull
    private Long activityId;


    @NotNull
     //"礼物名称")
    @Column(length = 120,unique = true)
    private  String giftName;


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

    @NotNull
     //"礼物图片名称")
    @Column(columnDefinition = "text")
    private   String imageName;
    @NotNull
    //"礼物图片路径")
    @Column(columnDefinition = "text")
    private  String imageUrl;
    @NotNull
     //"价格分为单位")
    private Integer price;
    public Integer getBeVote() {
        return beVote;
    }
    public void setBeVote(Integer beVote) {
        this.beVote = beVote;
    }
    //可以替代的票数
    private  Integer beVote=1;
     //"是否删除")
    private Boolean isDelete;
     //"创建时间")
    @Column(length = 50)
    private String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

}
