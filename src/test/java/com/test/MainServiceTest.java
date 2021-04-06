package com.test;

import com.demo.SpringBootDemoApplication;
import com.demo.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 22:54
 * Description: 邮箱测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringBootDemoApplication.class)
public class MainServiceTest {
    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendSimpleMail("接收邮箱地址","test simple mail"," hello this is simple mail");
    }

    @Test
    public void testHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello world ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail("接收邮箱地址","test simple mail",content);
    }

    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\Users\\kinbridge\\Pictures\\2018-08-20\\001.JPG";
        mailService.sendAttachmentsMail("接收邮箱地址", "主题：带附件的邮件", "有附件，请查收！", filePath);
    }
}
