package com.banana.message.consumer;

import com.banana.message.config.RabbitMQConfig;
import com.banana.message.vo.MailVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
@RabbitListener(queues = RabbitMQConfig.WE_CHAT_QUEUE)
public class MailConsumer {

    @Value("${spring.mail.username}")
    private String mailFrom;
    private JavaMailSender sender;

    public MailConsumer(JavaMailSender sender) {
        this.sender = sender;
    }

    @RabbitHandler
    public void consumerMailSend(String mailContext, Channel channel, Message message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            MailVo mailVo = mapper.readValue(mailContext, MailVo.class);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(mailVo.getTo());
            mailMessage.setSubject(mailVo.getSubject());
            mailMessage.setFrom(mailFrom);
            log.info(mailFrom);
            mailMessage.setText(mailVo.getText());
            sender.send(mailMessage);
        } catch (Exception e) {
            log.info("", e);
            log.info("邮件解析失败。。。");
        }
    }
}
