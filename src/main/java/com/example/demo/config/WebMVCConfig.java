package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author heshuhua343
 * @date
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.riskcontrol.test")
public class WebMVCConfig extends WebMvcConfigurerAdapter{

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    /**
     * 功能描述:
     * 
     * @author heshuhua343
     * @date 
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //指定静态资源
        registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
        super.addResourceHandlers(registry);
    }
}