package pers.mihao.toolset.client.net.http.dto;

import javax.validation.constraints.NotNull;

public class ReqAddCollection {
    @NotNull(message = "name不能为空")
    private String name;
    private String description;

    private Integer historyId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }
}
