package com.efan.core.primary;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 活动实体
 */
@Entity
@Table(name="activity")
public class Activity implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotNull
    @Description("标题")
    @Column(length = 120,unique = true)
    private  String title;
    @Max(2000)
    @NotNull
    @Column(length = 2000)
    @Description("内容")
    private  String content;
    @Description("截至时间")
    private Timestamp endTime;
    @NotNull
    @Description("活动规则")
    private  String rules;
    @Description("是否删除")
    private Boolean isDelete;
    @Description("创建时间")
    @Column(length = 120,unique = true)
    private String creationTime;
    @Description("参与者")
    private Integer actorCount;
    @Description("总投票数")
    private  Integer totalVotes;
    @Description("访问量")
    private Integer traffic;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
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

    public Integer getActorCount() {
        return actorCount;
    }

    public void setActorCount(Integer actorCount) {
        this.actorCount = actorCount;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }


}
