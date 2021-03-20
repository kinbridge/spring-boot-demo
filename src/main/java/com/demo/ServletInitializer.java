package com.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * SpringBootServletInitializer 可以使用外部的Servlet容器
 * 1.必须创建war项目，需要创建好web项目的目录。
 * 2.嵌入式Tomcat依赖scope指定provided。
 * 3.编写SpringBootServletInitializer类子类,并重写configure方法。
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootDemoApplication.class);
    }

}
