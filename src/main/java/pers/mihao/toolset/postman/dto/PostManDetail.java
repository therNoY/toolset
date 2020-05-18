package pers.mihao.toolset.postman.dto;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * 每个postman请求的细节
 */
public class PostManDetail implements Serializable {

    private List<RequestEntry> params;

    private MapEntry<String> auth;

    private List<RequestEntry> headers;

    private String body;


    public List<RequestEntry> getParams() {
        return params;
    }

    public void setParams(List<RequestEntry> params) {
        this.params = params;
    }

    public MapEntry<String> getAuth() {
        return auth;
    }

    public void setAuth(MapEntry<String> auth) {
        this.auth = auth;
    }

    public List<RequestEntry> getHeaders() {
        return headers;
    }

    public void setHeaders(List<RequestEntry> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class RequestEntry extends MapEntry<String>{
        private boolean s;
        private String d;

        public boolean getS() {
            return s;
        }

        public void setS(boolean s) {
            this.s = s;
        }

        public String getD() {
            return d;
        }

        public void setD(String d) {
            this.d = d;
        }
    }


    @Override
    public String toString() {
        return "PostManDetail{" +
                "params=" + params +
                ", auth=" + auth +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
