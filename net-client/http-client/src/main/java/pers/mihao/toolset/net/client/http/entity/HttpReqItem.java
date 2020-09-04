package pers.mihao.toolset.net.client.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import pers.mihao.toolset.net.client.http.consts.enums.ItemStatus;
import pers.mihao.toolset.net.client.http.dto.HttpReqDetail;
import pers.mihao.toolset.net.client.http.dto.MapEntry;

@TableName("http_req_item")
public class HttpReqItem implements Serializable {

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
     * {@link ItemStatus}
     */
    private Integer status;

    private Integer collectionsId;

    private LocalDateTime createDate;

    @JsonIgnore
    private String detail;

    @TableField(exist = false)
    private HttpReqDetail httpReqDetail;

    @JsonIgnore
    private String addHeaders;

    private Integer bodySize;
    private String contentType;
    @JsonIgnore
    private String cookies;
    @JsonIgnore
    private String headers;

    private Integer headersSize;

    @JsonIgnore
    private String resBody;

    private String statsMes;

    private Integer statusCode;

    private Long timer;
    @TableField(exist = false)
    private List<MapEntry<String>> respCookies;
    @TableField(exist = false)
    private List<MapEntry<String>> respHeaders;
    @TableField(exist = false)
    private List<MapEntry<String>> respAddHeaders;
    @TableField(exist = false)
    private Object respBody;

    public List<MapEntry<String>> getRespCookies() {
        return respCookies;
    }

    public void setRespCookies(List respCookies) {
        this.respCookies = respCookies;
    }

    public List<MapEntry<String>> getRespHeaders() {
        return respHeaders;
    }

    public void setRespHeaders(List respHeaders) {
        this.respHeaders = respHeaders;
    }

    public List<MapEntry<String>> getRespAddHeaders() {
        return respAddHeaders;
    }

    public void setRespAddHeaders(List respAddHeaders) {
        this.respAddHeaders = respAddHeaders;
    }

    public Object getRespBody() {
        return respBody;
    }

    public void setRespBody(Object respBody) {
        this.respBody = respBody;
    }

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

    public String getAddHeaders() {
        return addHeaders;
    }

    public void setAddHeaders(String addHeaders) {
        this.addHeaders = addHeaders;
    }

    public Integer getBodySize() {
        return bodySize;
    }

    public void setBodySize(Integer bodySize) {
        this.bodySize = bodySize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public Integer getHeadersSize() {
        return headersSize;
    }

    public void setHeadersSize(Integer headersSize) {
        this.headersSize = headersSize;
    }

    public String getResBody() {
        return resBody;
    }

    public void setResBody(String resBody) {
        this.resBody = resBody;
    }

    public String getStatsMes() {
        return statsMes;
    }

    public void setStatsMes(String statsMes) {
        this.statsMes = statsMes;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    @Override
    public String toString() {
        return "HttpReqItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", label='" + label + '\'' +
                ", status=" + status +
                ", collectionsId=" + collectionsId +
                ", createDate=" + createDate +
                ", detail='" + detail + '\'' +
                ", httpReqDetail=" + httpReqDetail +
                '}';
    }
}
