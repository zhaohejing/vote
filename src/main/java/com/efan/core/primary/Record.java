package com.efan.core.primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;



/**
 *投票记录
 */
@Entity
@Table(name="record")
public class Record implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    //"投票人key")
    @Column(length = 120,unique = true)
    private  String sendKey;
    @NotNull
    //"接受人")
    private Long actorId;

    public Boolean getGift() {
        return isGift;
    }

    public void setGift(Boolean gift) {
        isGift = gift;
    }

    private Boolean isGift;
    //票数
    private  Integer votes;

    //"是否删除")
    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendKey() {
        return sendKey;
    }

    public void setSendKey(String sendKey) {
        this.sendKey = sendKey;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
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

    //"投票时间")
    private String creationTime;
}
