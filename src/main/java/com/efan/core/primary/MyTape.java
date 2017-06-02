package com.efan.core.primary;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 我的录音实体
 */
@Entity
@Table(name="efan_mytape")
public class MyTape implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //骑牛url
    @NotNull
    @Column(length = 50)
    private String qiniuUrl;
    //用户唯一标识
    @NotNull
    @Column(length = 50)
    private String userKey;
    //歌曲名称
    @Column(length = 50)
    private  String  songName;
    //演唱者
    @Column(length = 50)
    private  String singer;
    //原唱
    @Column(length = 50)
    private  String originalSinger;
    //时长
    @Column(length = 50)
    private  String songTime;
    //图片
    @Column(length = 150)
    private  String userImage;
    //图片
    @Column(length = 150)
    private  String songImage;
    //留言
    @Column(length = 150)
    private  String remark;
    //是否已上传
    private  Boolean state;
    private String creationTime;
    private String modifyTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQiniuUrl() {
        return qiniuUrl;
    }

    public void setQiniuUrl(String qiniuUrl) {
        this.qiniuUrl = qiniuUrl;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getOriginalSinger() {
        return originalSinger;
    }

    public void setOriginalSinger(String originalSinger) {
        this.originalSinger = originalSinger;
    }

    public String getSongTime() {
        return songTime;
    }

    public void setSongTime(String songTime) {
        this.songTime = songTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
