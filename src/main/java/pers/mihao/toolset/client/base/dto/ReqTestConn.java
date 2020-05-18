package pers.mihao.toolset.client.base.dto;


import javax.validation.constraints.NotBlank;

public class ReqTestConn extends BaseRequestDto{
    private String name;
    @NotBlank(message = "host 不能为空")
    private String host;
    @NotBlank(message = "port 不能为空")
    private Integer port;

    private String userName;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

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
