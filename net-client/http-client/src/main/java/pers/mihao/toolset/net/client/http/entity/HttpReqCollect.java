package pers.mihao.toolset.net.client.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-02-06
 */
@TableName("http_req_collect")
public class HttpReqCollect implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    protected Integer userId;

    protected Integer historyId;

    private String name;

    private String description;

    /* 子收藏 */
    @TableField(exist = false)
    private List<HttpReqCollect> childCollection;

    /* 收藏下面的item */
    @TableField(exist = false)
    private List<HttpReqItem> childItem;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public List<HttpReqCollect> getChildCollection() {
        return childCollection;
    }

    public void setChildCollection(List<HttpReqCollect> childCollection) {
        this.childCollection = childCollection;
    }

    public List<HttpReqItem> getChildItem() {
        return childItem;
    }

    public void setChildItem(List<HttpReqItem> childItem) {
        this.childItem = childItem;
    }


    @Override
    public String toString() {
        return "HttpReqCollect{" +
        "id=" + id +
        ", userId=" + userId +
        ", name=" + name +
        ", description=" + description +
        ", historyId=" + historyId +
        "}";
    }
}
