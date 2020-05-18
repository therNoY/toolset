package pers.mihao.toolset.music.common.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HTTPBasicFiler());
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        return registration;
    }


}