package com.swagger2.customerswaggerstarter.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author sunchuanyin
 * @version 1.0
 * @description 这个类主要是读取yml文件中关于swagger2的配置信息
 * @date 2021/3/23 10:47 上午
 */
// @Component // 如果在对应的配置类中的添加了注解EnableConfigurationProperties，可以不需要@Component注解
@ConfigurationProperties("system.swagger2")
@Data
public class Swagger2ConfigProperties {

    /**
     * 扫描包路径
     */
    private String basePackage;

    /**
     * swagger头标
     */
    private String title;

    /**
     * swagger描述
     */
    private String description;
}
