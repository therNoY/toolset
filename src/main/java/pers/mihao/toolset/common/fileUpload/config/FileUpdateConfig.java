package pers.mihao.toolset.common.fileUpload.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;

/**
 * 默认扫描启动类所在包的所有子包，这里是App类所在包及其子包
 * @author ljf
 *
 */
@Configurable
public class FileUpdateConfig {

    /**
     * 配置文件上传大小
     */
    @Bean
    public MultipartConfigElement getMultiConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.of(3000, DataUnit.MEGABYTES));
        factory.setMaxRequestSize(DataSize.of(3000, DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }

}
