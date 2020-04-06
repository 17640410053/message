package com.banana.message.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String WE_CHAT_EXCHANGE = "MAIL-EXCHANGE";
    public static final String WE_CHAT_QUEUE = "MAIL-QUEUE";
    public static final String WE_CHAT_ROUTING_KEY = "MAIL-ROUTING-KEY";

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange weChatExchange() {
        return new DirectExchange(WE_CHAT_EXCHANGE);
    }

    /**
     * 获取队列B
     */
    @Bean
    public Queue queueWeChat() {
        return new Queue(WE_CHAT_QUEUE, true); //队列持久
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueWeChat()).to(weChatExchange()).with(RabbitMQConfig.WE_CHAT_ROUTING_KEY);
    }
}
