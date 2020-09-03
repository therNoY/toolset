package pers.mihao.toolset.client.net.http.dto;

import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.URL;
import pers.mihao.toolset.annotation.InEnum;
import pers.mihao.toolset.enums.HttpMethod;

public class ReqAddHistory {

    @InEnum(in = HttpMethod.class, message = "type不能为空")
    private String type;
    @URL(message = "url 必须是URL类型")
    private String url;

    private JSONObject postManDetail;

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

    public JSONObject getPostManDetail() {
        return postManDetail;
    }

    public void setPostManDetail(JSONObject postManDetail) {
        this.postManDetail = postManDetail;
    }
}
