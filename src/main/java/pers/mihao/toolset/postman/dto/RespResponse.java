package pers.mihao.toolset.postman.dto;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Map;


/**
 * 一次发送http请求的返回值
 */
public class RespResponse {

    private int statusCode;

    private String statsMes;

    private Long timer;

    private Integer bodySize;

    private Integer headersSize;

    private String contentType;

    private List<MapEntry<String>> cookies;

    private List<MapEntry<String>> headers;

    private List<MapEntry<String>> addHeaders;

    private Object resBody;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<MapEntry<String>> getCookies() {
        return cookies;
    }

    public void setCookies(List<MapEntry<String>> cookies) {
        this.cookies = cookies;
    }

    public List<MapEntry<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(List<MapEntry<String>> headers) {
        this.headers = headers;
    }

    public Object getResBody() {
        return resBody;
    }

    public void setResBody(Object resBody) {
        this.resBody = resBody;
    }

    public String getStatsMes() {
        return statsMes;
    }

    public void setStatsMes(String statsMes) {
        this.statsMes = statsMes;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public Integer getBodySize() {
        return bodySize;
    }

    public void setBodySize(Integer bodySize) {
        this.bodySize = bodySize;
    }

    public Integer getHeadersSize() {
        return headersSize;
    }

    public void setHeadersSize(Integer headersSize) {
        this.headersSize = headersSize;
    }

    public String getContentType() {
        return contentType;
    }

    public List<MapEntry<String>> getAddHeaders() {
        return addHeaders;
    }

    public void setAddHeaders(List<MapEntry<String>> addHeaders) {
        this.addHeaders = addHeaders;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
