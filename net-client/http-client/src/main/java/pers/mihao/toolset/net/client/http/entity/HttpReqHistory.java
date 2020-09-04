package pers.mihao.toolset.net.client.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import pers.mihao.toolset.net.client.http.dto.HttpReqDetail;

@TableName("http_req_history")
public class HttpReqHistory implements Serializable {

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
    private HttpReqDetail httpReqDetail;

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

    public HttpReqDetail getHttpReqDetail() {
        return httpReqDetail;
    }

    public void setHttpReqDetail(HttpReqDetail httpReqDetail) {
        this.httpReqDetail = httpReqDetail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "HttpReqHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", createDate=" + createDate +
                ", detail='" + detail + '\'' +
                ", httpReqDetail=" + httpReqDetail +
                '}';
    }
}
