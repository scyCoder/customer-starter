package com.swagger2.customerswaggerstarter.conf;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author sunchuanyin
 * @version 1.0
 * @description Swagger2的相关配置
 * @date 2021/3/23 10:52 上午
 */
@EnableSwagger2
// @EnableConfigurationProperties这个注解是将Swagger2ConfigProperties注入到容器中如果在Swagger2ConfigProperties类中只写了@ConfigurationProperties注解，这个注解是必须出现的，否则会报错原因是Swagger2ConfigProperties这个类没有注入到spring容器中@ConfigurationProperties这个注解找不到，此时需要@EnableConfigurationProperties这个注解的出现，而如果在Swagger2ConfigProperties这个类中添加了@Component注解说明把Swagger2ConfigProperties类注入到了spring容器中，此时不会报错
@EnableConfigurationProperties(Swagger2ConfigProperties.class)
//从配置文件读取键为system.swagger2.enabled的信息，如果值与havingValue的值匹配则将被修改的类注入到spring容器中如果，不匹配不注入到spring容器中。matchIfMissing：如果配置文件中缺少system.swagger2.enabled，如果matchIfMissing为true，没有system.test也会正常加载(注入到spring容器中)；反之报错
@ConditionalOnProperty(value = "system.swagger2.enabled", havingValue = "true", matchIfMissing = true)
public class Swagger2Configuration {

    @Autowired
    private Swagger2ConfigProperties swagger2ConfigProperties;


    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //此包路径下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage(swagger2ConfigProperties.getBasePackage()))
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()));
    }

    /***
     * oauth2配置
     * 需要增加swagger授权回调地址
     * http://localhost:8888/webjars/springfox-swagger-ui/o2c.html
     * @return
     */
    @Bean
    SecurityScheme securityScheme() {
        return new ApiKey("X-Access-Token", "X-Access-Token", "header");
    }

    /**
     * api文档的详细信息函数,注意这里的注解引用的是哪个
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title(swagger2ConfigProperties.getTitle())
                // 版本号
                .version("1.0")
                // 接口文档描述
                .description(swagger2ConfigProperties.getDescription())
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }
}
