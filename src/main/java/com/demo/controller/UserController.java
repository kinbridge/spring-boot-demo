package com.demo.controller;

import com.demo.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * Author:   kinbridge
 * Date:     2021/3/31 21:59
 * Description: redis自动缓存
 * 访问地址：http://localhost:8080/redis/getUser
 */
@RestController
@RequestMapping("/redis")
public class UserController {

    @RequestMapping("/getUser")
    // 其中 value 的值就是缓存到 Redis 中的 key
    @Cacheable(value="user-key")
    public User getUser() {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");
        return user;
    }

    @RequestMapping("/uid")
    String uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return session.getId();
    }
}
