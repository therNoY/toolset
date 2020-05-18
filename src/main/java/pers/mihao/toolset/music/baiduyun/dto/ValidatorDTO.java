package pers.mihao.toolset.music.baiduyun.dto;

import java.util.Map;

public class ValidatorDTO {
    private String postUrl;
    private Map<String, String> postData;
    private String cookie;
    private Response20 response20;
    private String realDownloadURL;


    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public Map<String, String> getPostData() {
        return postData;
    }

    public void setPostData(Map<String, String> postData) {
        this.postData = postData;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Response20 getResponse20() {
        return response20;
    }

    public void setResponse20(Response20 response20) {
        this.response20 = response20;
    }

    public String getRealDownloadURL() {
        return realDownloadURL;
    }

    public void setRealDownloadURL(String realDownloadURL) {
        this.realDownloadURL = realDownloadURL;
    }
}
