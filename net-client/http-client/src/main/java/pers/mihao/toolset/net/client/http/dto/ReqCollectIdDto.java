package pers.mihao.toolset.net.client.http.dto;

public class ReqCollectIdDto {
    private Integer collectId;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCollectId() {
        return collectId;
    }

    public void setCollectId(Integer collectId) {
        this.collectId = collectId;
    }
}
