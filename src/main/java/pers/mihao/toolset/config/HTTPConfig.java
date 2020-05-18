package pers.mihao.toolset.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HTTPConfig {
    //首先，在springboot启动类中，或者配置类中进行配置
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        //使用build()方法进行获取
        return restTemplateBuilder.build();
    }

}
