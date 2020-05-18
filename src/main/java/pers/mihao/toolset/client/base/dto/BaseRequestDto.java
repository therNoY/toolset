package pers.mihao.toolset.client.base.dto;

import javax.validation.constraints.NotBlank;

public class BaseRequestDto {
    @NotBlank(message = "type 不能为空")
    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
