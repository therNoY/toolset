package pers.mihao.toolset.client.net.http.dto;

import java.util.List;
import java.util.Map;
import pers.mihao.toolset.client.net.http.entity.PostmanItem;

public class RespPostManHistory {

    private Map<String, List<PostmanItem>> history;

    public RespPostManHistory() {
        this.history = history;
    }

    public Map<String, List<PostmanItem>> getHistory() {
        return history;
    }

    public void setHistory(Map<String, List<PostmanItem>> history) {
        this.history = history;
    }
}
