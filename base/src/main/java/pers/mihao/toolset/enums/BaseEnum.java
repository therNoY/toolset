package pers.mihao.toolset.enums;

import java.util.HashMap;
import java.util.Map;
import pers.mihao.toolset.util.StringUtil;

public interface BaseEnum {

    Map<String, String> enumMap = new HashMap();

    /**
     * 单位枚举 获取驼峰命名的type 将枚举改成小写 ex WATER_ELEMENT => waterElement
     *
     * @return
     */
    default String type() {
        String key = this.toString();
        if (enumMap.get(key) != null) {
            return enumMap.get(key);
        } else {
            String type = StringUtil.underscoreToHump(key);
            enumMap.put(key, type);
            return type;
        }
    }
}
