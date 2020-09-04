package pers.mihao.toolset.net.client.http.dto;

import javax.validation.constraints.NotNull;

public class ReqUpdateStatus {
    @NotNull(message = "id 不能为空")
    private Integer id;
    @NotNull(message = "status 不能为空")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
