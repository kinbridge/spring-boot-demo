package com.test;

import com.demo.SpringBootDemoApplication;
import com.demo.rabbit.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 21:59
 * Description: rabbitMq测试
 */
@SpringBootTest(classes=SpringBootDemoApplication.class)
@RunWith(SpringRunner.class)
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void hello() throws Exception {
        helloSender.send();
    }

}