package com.demo.service;

/**
 * Author:   kinbridge
 * Date:     2021/4/6 22:45
 * Description:
 */
public  interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
