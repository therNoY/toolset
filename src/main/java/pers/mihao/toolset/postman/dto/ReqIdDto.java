package pers.mihao.toolset.postman.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

public class ReqIdDto {
    @DecimalMin(value = "0", message = "id不能为空")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
