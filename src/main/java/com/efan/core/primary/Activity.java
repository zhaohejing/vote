package com.efan.core.primary;


import javax.persistence.*;
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
     //"标题"
    @Column(length = 200,unique = true)
    private  String title;
     //"内容"
    private  String content;
     //"截至时间")
    private Timestamp endTime;

     //"活动规则")
    private  String rules;
     //"是否删除")
    private Boolean isDelete;

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    //"是否发布")
    private Boolean isPublic=false;
     //"创建时间")
    @Column(length = 120,unique = true)
    private String creationTime;
     //"参与者")
    private Integer actorCount;
     //"总投票数")
    private  Integer totalVotes;
     //"访问量")
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
