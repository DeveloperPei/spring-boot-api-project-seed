package com.bzm.configurer;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI  //第三方swagger增强API注解
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        /**
         *
         * @author px
         * @createTime: 2020/4/18 0018
         * @param: []
         * @return :springfox.documentation.spring.web.plugins.Docket
         * */
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("接口文档1")
                .apiInfo(apiInfo())
                .host("localhost:8080")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bzm.controller"))
                .paths(PathSelectors.any())
                .build();
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("XX项目API")
                .description("系统化信息化XX平台,为您提供最优质的服务")
                .termsOfServiceUrl("http://localhost:8080/doc.html")
                .version("1.0")
                .build();
    }
}