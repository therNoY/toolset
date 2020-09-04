package pers.mihao.toolset.net.client.http.dto;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.URL;
import pers.mihao.toolset.common.annotation.InEnum;
import pers.mihao.toolset.enums.HttpMethod;

public class ReqAddHistory {

    @InEnum(in = HttpMethod.class, message = "type不能为空")
    private String type;
    @URL(message = "url 必须是URL类型")
    private String url;

    private JSONObject httpReqDetail;

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

    public JSONObject getHttpReqDetail() {
        return httpReqDetail;
    }

    public void setHttpReqDetail(JSONObject httpReqDetail) {
        this.httpReqDetail = httpReqDetail;
    }
}
