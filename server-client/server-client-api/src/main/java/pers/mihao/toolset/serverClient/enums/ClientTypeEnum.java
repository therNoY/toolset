package pers.mihao.toolset.serverClient.enums;

import pers.mihao.toolset.enums.BaseEnum;

public enum ClientTypeEnum implements BaseEnum {

    ZOOKEEPER("ZookeeperService"),
    REDIS("ZookeeperService"),
    MONGO("ZookeeperService"),
    MYSQL("ZookeeperService"),
    ORACLE("ZookeeperService");

    public String serverName;

    ClientTypeEnum(String serverName) {
        this.serverName = serverName;
    }
}
