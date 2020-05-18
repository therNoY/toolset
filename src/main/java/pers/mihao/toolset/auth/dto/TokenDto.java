package pers.mihao.toolset.auth.dto;

import javax.validation.constraints.NotBlank;

public class TokenDto {
    @NotBlank(message = "token 不能为空")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
