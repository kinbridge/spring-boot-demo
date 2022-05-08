package com.neo.banner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bridge
 * @Date 2022/04/22/22:52
 */
@RestController
public class BannerController {

    @GetMapping("/")
    public List<String> getBanner() {
        List<String> list = new ArrayList();
        list.add("<!DOCTYPE html>");
        list.add("<html>");
        list.add("   <head>");
        list.add("      <title>HTML dir Tag</title>");
        list.add("   </head>");
        list.add("   <body>");
        list.add("      <p>The following are the values:</p>");
        list.add("      <dir>");
        list.add("<li> ......................我佛慈悲......................<li>");
        list.add("<li>                       _oo0oo_                       <li>");
        list.add("<li>                      o8888888o                      <li>");
        list.add("<li>                      88\" . \"88                    <li>");
        list.add("<li>                      (| -_- |)                      <li>");
        list.add("<li>                      0\\  =  /0                     <li>");
        list.add("<li>                    ___/‘---’\\___                   <li>");
        list.add("<li>                  .' \\|       |/ '.                 <li>");
        list.add("<li>                 / \\\\|||  :  |||// \\              <li>");
        list.add("<li>                / _||||| -卍-|||||_ \\               <li>");
        list.add("<li>               |   | \\\\\\  -  /// |   |            <li>");
        list.add("<li>               | \\_|  ''\\---/''  |_/ |             <li>");
        list.add("<li>               \\  .-\\__  '-'  ___/-. /             <li>");
        list.add("<li>             ___'. .'  /--.--\\  '. .'___            <li>");
        list.add("<li>          .\"\" ‘<  ‘.___\\_<|>_/___.’ >’ \"\".      <li>");
        list.add("<li>         | | :  ‘- \\‘.;‘\\ _ /’;.’/ - ’ : | |       <li>");
        list.add("<li>         \\  \\ ‘_.   \\_ __\\ /__ _/   .-’ /  /     <li>");
        list.add("<li>     =====‘-.____‘.___ \\_____/___.-’___.-’=====     <li>");
        list.add("<li>                       ‘=---=’                       <li>");
        list.add("<li>                                                     <li>");
        list.add("<li>....................佛祖开光 ,永无BUG................<li>");
        list.add("      </dir>");
        list.add("   </body>");
        list.add("</html>");
        return list;
    }

}
