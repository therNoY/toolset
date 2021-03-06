package pers.mihao.toolset.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "用户名/邮箱不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
