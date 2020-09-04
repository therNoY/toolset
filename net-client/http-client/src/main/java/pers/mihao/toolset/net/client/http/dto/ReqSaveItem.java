package pers.mihao.toolset.net.client.http.dto;

import com.alibaba.fastjson.JSONObject;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;
import pers.mihao.toolset.annotation.InEnum;
import pers.mihao.toolset.enums.HttpMethod;

public class ReqSaveItem {

    @InEnum(in = HttpMethod.class, message = "type不能为空")
    private String type;
    @URL(message = "label 必须是URL类型")
    private String label;

    private JSONObject httpReqDetail;

    @NotNull(message = "历史Id 不能为空")
    private Integer historyId;

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public JSONObject getHttpReqDetail() {
        return httpReqDetail;
    }

    public void setHttpReqDetail(JSONObject httpReqDetail) {
        this.httpReqDetail = httpReqDetail;
    }
}
