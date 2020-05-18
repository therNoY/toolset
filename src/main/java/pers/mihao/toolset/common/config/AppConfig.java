package pers.mihao.toolset.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.mihao.toolset.common.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 包含和应用有关的配置
 */
public class AppConfig {
    static Logger log = LoggerFactory.getLogger(AppConfig.class);
    static Map<String, String> map;

    static {
        map = new HashMap<>();
        Properties properties = PropertiesUtil.getProperties("application-setting.properties");
        properties.forEach((key, value)->{
            map.put(key.toString(), value.toString());
        });
    }


    public static String get(String key) {
        String info = map.get(key);
        if (info == null) {
            log.error("没有App配置 {}", key);
            return "-1";
        }else {
            return info;
        }
    }
}
