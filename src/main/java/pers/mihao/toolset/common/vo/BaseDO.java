package pers.mihao.toolset.common.vo;

import java.io.Serializable;

public class BaseDO implements Serializable {
    protected Integer uuid;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }
}
