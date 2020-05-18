package pers.mihao.toolset.postman.dto;

import pers.mihao.toolset.postman.entity.PostmanItem;

import java.util.List;
import java.util.Map;

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
