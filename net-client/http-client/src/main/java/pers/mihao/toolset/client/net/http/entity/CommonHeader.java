package pers.mihao.toolset.client.net.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-01-24
 */
public class CommonHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String key;

    /**
     * 默认
     */
    private String defaultValue;

    /**
     * 是否默认携带
     */
    private Integer defaultUse;

    /**
     * 描述
     */
    private String des;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
    public Integer getDefaultUse() {
        return defaultUse;
    }

    public void setDefaultUse(Integer defaultUse) {
        this.defaultUse = defaultUse;
    }
    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "CommonHeader{" +
        "id=" + id +
        ", key=" + key +
        ", defaultValue=" + defaultValue +
        ", defaultUse=" + defaultUse +
        ", des=" + des +
        "}";
    }
}
