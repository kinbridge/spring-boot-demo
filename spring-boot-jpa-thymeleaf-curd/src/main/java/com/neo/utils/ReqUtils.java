package com.neo.utils;

import org.apache.commons.text.StrSubstitutor;
import org.apache.commons.text.StringSubstitutor;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bridge
 * @Date 2022/01/09/12:32
 */
public class ReqUtils {
    public static void main(String[] args) {

        String name = "张三";
        int age = 16;
        String str1 = "我叫%s，年龄%s";
        String context = String.format(str1, name, age);
        System.out.println("context: " + context);

        System.out.println("-----------------------");

        String st2 = "我叫{0}，年龄{1}";
        String context2 = MessageFormat.format(st2, name, age);
        System.out.println("context2: " + context2);

        System.out.println("-----------------------");

        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "16");
        
//        StrSubstitutor strSubstitutor = new StrSubstitutor(map);
        StringSubstitutor strSubstitutor = new StringSubstitutor();
        String str3 = "我叫${name}，年龄${age}";
        String context3 = strSubstitutor.replace(str3);
        System.out.println("context3: " + context3);
    }
}
