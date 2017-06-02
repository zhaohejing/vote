package com.efan.core.primary;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;



/**
 * 送礼榜单
 */
@Entity
@Table(name="giving")
public class Giving implements Serializable {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSendImage() {
        return sendImage;
    }

    public void setSendImage(String sendImage) {
        this.sendImage = sendImage;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getGiftId() {
        return giftId;
    }

    public void setGiftId(Long giftId) {
        this.giftId = giftId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Description("送礼人")
    @Column(length = 120,unique = true)
    private  String sendName;

    @Description("送礼人头像")
    @Column(length = 300)
    private  String sendImage;
    @NotNull
    @Description("接受人")
    private Long actorId;

    @NotNull
    @Description("礼物id")
    private Long giftId;

    @Description("是否删除")
    private Boolean isDelete;
    @Description("送礼时间")
    private Timestamp creationTime;
}
