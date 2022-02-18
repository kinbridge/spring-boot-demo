package com.neo.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author bridge
 * @Date 2022/01/04/23:34
 */
public class ReqRexUtils {

    /**
     *
     * @param content  内容
     * @param map     参数map
     * @return
     */
    public static String renderString(String content, Map<String, String> map){
        Set<Map.Entry<String, String>> sets = map.entrySet();
        for(Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue());
        }
        return content;
    }

    public static void main(String[] args) {
        String content = "hello ${name}, 1 2 3 4 5 ${six} 7, again ${name}. ";
        Map<String, String> map = new HashMap<>();
        map.put("name", "java");
        map.put("six", "6");
        content = ReqRexUtils.renderString(content, map);
        System.out.println(content);
    }
}
