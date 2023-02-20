package cn.itcast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 自动配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerAutoConfiguration {
    @Bean
    public Docket createRestApi1(){
        ApiInfo apiInfo = new ApiInfoBuilder().title("我的接口文档").contact(new Contact("itcast","http://www.itcast.cn","hello@itcast.cn")).version("1.0").description("接口文档描述").build();

        //docket对象用于封装接口文档相关信息
        Docket docket = new Docket(DocumentationType.SWAGGER_2).
                        apiInfo(apiInfo).
                        groupName("用户接口组").select().
                        apis(RequestHandlerSelectors.basePackage("cn.itcast.controller.user")).
                        build();
        return docket;
    }
    @Bean
    public Docket createRestApi2(){
        ApiInfo apiInfo = new ApiInfoBuilder().title("我的接口文档").contact(new Contact("itcast","http://www.itcast.cn","hello@itcast.cn")).version("1.0").description("接口文档描述").build();

        //docket对象用于封装接口文档相关信息
        Docket docket = new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiInfo).
                groupName("菜单接口组").select().
                apis(RequestHandlerSelectors.basePackage("cn.itcast.controller.menu")).
                build();
        return docket;
    }
}
