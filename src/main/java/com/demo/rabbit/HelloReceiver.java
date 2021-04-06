package com.demo.rabbit;

import com.demo.model.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 21:21
 * Description: 接收者
 */
@Component
@RabbitListener(queues = "hello")
public class HelloReceiver {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver  : " + hello);
    }

//    //接收者
//    @RabbitHandler
//    public void process(User user) {
//        System.out.println("Receiver object : " + user);
//    }

}
