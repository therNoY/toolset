package pers.mihao.toolset.net.client.http.dto;

import java.util.List;
import java.util.Map;

import pers.mihao.toolset.net.client.http.entity.HttpReqItem;

public class RespPostManHistory {

    private Map<String, List<HttpReqItem>> history;

    public RespPostManHistory() {
        this.history = history;
    }

    public Map<String, List<HttpReqItem>> getHistory() {
        return history;
    }

    public void setHistory(Map<String, List<HttpReqItem>> history) {
        this.history = history;
    }
}
