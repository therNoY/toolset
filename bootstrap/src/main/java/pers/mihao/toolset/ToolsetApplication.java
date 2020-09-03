package pers.mihao.toolset;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAspectJAutoProxy // 开启aop
@EnableTransactionManagement // 开启事务
@MapperScan("pers.mihao.toolset.**.dao")
@EnableScheduling
@SpringBootApplication
@RestController
public class ToolsetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolsetApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    /**
     * mybatis 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }
}
