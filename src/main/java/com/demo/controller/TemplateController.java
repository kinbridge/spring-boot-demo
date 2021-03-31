package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Author:   kinbridge
 * Date:     2021/3/31 22:49
 * Description:
 */
@Controller
public class TemplateController {
    /**
     * 返回html模板.
     */
    @RequestMapping("/helloHtml")
    public String helloHtml(Map<String,Object> map){
        map.put("title","from TemplateController.helloHtml");
        return"/helloHtml";
    }
}