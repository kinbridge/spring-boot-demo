package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Author:   kinbridge
 * Date:     2021/3/20 23:15
 * Description:自定义配置类
 */
@Component
public class NeoProperties {
    @Value("${com.demo.title}")
    private String title;
    @Value("${com.demo.description}")
    private String description;



}
