package com.example.demo.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author heshuhua343
 * @date 
 */
@Configuration
public class MultipartConfig {

    /**  
     * 文件上传配置  
     * @return  
     */  
    @Bean
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("5MB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("5MB");  
        return factory.createMultipartConfig();  
    }  

}