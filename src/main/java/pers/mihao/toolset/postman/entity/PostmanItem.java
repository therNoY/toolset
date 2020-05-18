package pers.mihao.toolset.postman.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import pers.mihao.toolset.postman.dto.PostManDetail;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostmanItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    @JsonIgnore
    private String url;

    private String name;

    @TableField(exist = false)
    private String label;

    private Integer userId;

    private Integer lastVersionId;

    private Integer historyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    /**
     * {@link pers.mihao.toolset.postman.consts.ItemStatus}
     */
    private Integer status;

    private Integer collectionsId;

    private LocalDateTime createDate;

    @JsonIgnore
    private String detail;

    @TableField(exist = false)
    private PostManDetail postManDetail;

    public Integer getLastVersionId() {
        return lastVersionId;
    }

    public void setLastVersionId(Integer lastVersionId) {
        this.lastVersionId = lastVersionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public PostManDetail getPostManDetail() {
        return postManDetail;
    }

    public void setPostManDetail(PostManDetail postManDetail) {
        this.postManDetail = postManDetail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCollectionsId() {
        return collectionsId;
    }

    public void setCollectionsId(Integer collectionsId) {
        this.collectionsId = collectionsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PostmanItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", status=" + status +
                ", collectionsId=" + collectionsId +
                ", createDate=" + createDate +
                ", detail='" + detail + '\'' +
                ", postManDetail=" + postManDetail +
                '}';
    }
}
