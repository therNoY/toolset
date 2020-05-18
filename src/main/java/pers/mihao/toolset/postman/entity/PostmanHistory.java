package pers.mihao.toolset.postman.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import pers.mihao.toolset.postman.dto.PostManDetail;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostmanHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属用户Id
     */
    private Integer userId;

    private String type;

    @JsonIgnore
    private String url;

    @TableField(exist = false)
    private String label;

    private LocalDateTime createDate;

    @JsonIgnore
    private String detail;

    @TableField(exist = false)
    private PostManDetail postManDetail;

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

}
