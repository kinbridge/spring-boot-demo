package com.demo.rabbit;

import com.demo.model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 21:20
 * Description: 发送者
 */
@Component
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

//    //发送者
//    public void send(User user) {
//        System.out.println("Sender object: " + user.toString());
//        this.rabbitTemplate.convertAndSend("object", user);
//    }

}