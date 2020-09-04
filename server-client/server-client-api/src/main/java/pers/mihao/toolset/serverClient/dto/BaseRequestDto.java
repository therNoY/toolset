package pers.mihao.toolset.serverClient.dto;

import javax.validation.constraints.NotBlank;

/**
 * 客户端专用的连接
 */
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
