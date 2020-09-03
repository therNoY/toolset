package pers.mihao.toolset.client.net.http.dto;

public class ReqSaveItemDto {

    private Integer id;

    private Boolean remove;

    public Boolean getRemove() {
        return remove;
    }

    public void setRemove(Boolean remove) {
        this.remove = remove;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
