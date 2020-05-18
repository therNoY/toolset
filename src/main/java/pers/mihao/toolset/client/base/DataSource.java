package pers.mihao.toolset.client.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * 需要连接的基础类
 */
public abstract class DataSource implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String DEFAULT_HOST = "localhost";
    public static String DEFAULT_TIMEOUT = "5000";

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String host;
    private Integer port;

    private String userName;
    private String password;
    private String userId;

    @TableField(exist = false)
    private ClientNode clientNode;

    public ClientNode getClientNode() {
        return clientNode;
    }

    public void setClientNode(ClientNode clientNode) {
        this.clientNode = clientNode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "ZkSource{" +
                "id=" + id +
                ", host=" + host +
                ", port=" + port +
                ", userName=" + userName +
                ", password=" + password +
                ", userId=" + userId +
                "}";
    }
}
