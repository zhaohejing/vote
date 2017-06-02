package com.efan.core.primary;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


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

    @NotNull
     //"礼物名称")
    @Column(length = 120,unique = true)
    private  String giftName;

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    @NotNull
     //"礼物图片路径")
    @Column(length = 300,unique = true)
    private  String giftImage;

    @NotNull
     //"价格分为单位")
    private Integer price;
     //"是否删除")
    private Boolean isDelete;
     //"创建时间")
    @Column(length = 30)
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
