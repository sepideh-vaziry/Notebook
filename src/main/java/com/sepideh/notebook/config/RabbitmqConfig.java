package com.sepideh.notebook.config;

import com.sepideh.notebook.rabbitmqtest.RabbitMQMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String TOPIC_EXCHANGE_NAME = "exchangeNameTest";
    public static final String ROUTING_KEY_TEST = "routing.test.%s";

    private static final String QUEUE_NAME = "myQueue";

    //******************************************************************************************************************
    @Bean
    public Queue createQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    //******************************************************************************************************************
    @Bean
    public TopicExchange createTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    //******************************************************************************************************************
    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
            .bind(queue)
            .to(topicExchange)
            .with(String.format(ROUTING_KEY_TEST, "#"));
    }

    //******************************************************************************************************************
    @Bean
    public ConnectionFactory createConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");

        return cachingConnectionFactory;
    }

    //******************************************************************************************************************
    @Bean
    public MessageListenerContainer createMessageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(createConnectionFactory());
        simpleMessageListenerContainer.setQueues(createQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());

        return simpleMessageListenerContainer;
    }

}