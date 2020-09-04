package pers.mihao.toolset.auth.dto;


import pers.mihao.toolset.auth.entity.User;

public class RespAuthDao {

    private User user;
    private String token;

    public RespAuthDao(User user, String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
