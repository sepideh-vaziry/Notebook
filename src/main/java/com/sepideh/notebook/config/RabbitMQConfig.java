package com.sepideh.notebook.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TOPIC_EXCHANGE_USER = "exchangeNameUser";

    public static final String ROUTING_KEY_USER = "routing.user.%s";
    public static final String ROUTING_KEY_USERNAME = "routing.username.%s";
    public static final String ROUTING_KEY_ID = "routing.id.%s";

    public static final String QUEUE_NAME_USER = "queue_user";
    public static final String QUEUE_NAME_USERNAME = "queue_username";
    public static final String QUEUE_NAME_ID = "queue_id";

    //******************************************************************************************************************
    @Bean
    @Qualifier(value = "userQueue")
    public Queue createUserQueue() {
        return new Queue(QUEUE_NAME_USER, true);
    }

    @Bean
    @Qualifier(value = "usernameQueue")
    public Queue createUsernameQueue() {
        return new Queue(QUEUE_NAME_USERNAME, true);
    }

    @Bean
    @Qualifier(value = "idQueue")
    public Queue createIdQueue() {
        return new Queue(QUEUE_NAME_ID, true);
    }

    //******************************************************************************************************************
    @Bean
    public TopicExchange createTopicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_USER);
    }

    //******************************************************************************************************************
    @Bean
    public Binding bindingUserQueue(@Qualifier("userQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
            .bind(queue)
            .to(topicExchange)
            .with(String.format(ROUTING_KEY_USER, "*"));
    }

    @Bean
    public Binding bindingUsernameQueue(@Qualifier("usernameQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
            .bind(queue)
            .to(topicExchange)
            .with(String.format(ROUTING_KEY_USERNAME, "*"));
    }

    @Bean
    public Binding bindingIdQueue(@Qualifier("idQueue") Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
            .bind(queue)
            .to(topicExchange)
            .with(String.format(ROUTING_KEY_ID, "*"));
    }

    //******************************************************************************************************************
//    @Bean(name = "myProjectConnectionFactory")
//    public CachingConnectionFactory createConnectionFactory() {
//        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
//        cachingConnectionFactory.setUsername("guest");
//        cachingConnectionFactory.setPassword("guest");
//
//        return cachingConnectionFactory;
//    }
//
//    //******************************************************************************************************************
//    @Bean
//    public MessageListenerContainer createMessageListenerContainer(
//        @Qualifier("myProjectConnectionFactory") CachingConnectionFactory cachingConnectionFactory,
//        Queue queue,
//        MessageListenerAdapter messageListenerAdapter
//    ) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(cachingConnectionFactory);
//        simpleMessageListenerContainer.setQueues(queue);
////        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());
//        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
//
//        return simpleMessageListenerContainer;
//    }
//
//    //******************************************************************************************************************
//    @Bean
//    public MessageListenerAdapter listenerAdapter(RabbitMQUserConsumer rabbitMQUserConsumer) {
//        return new MessageListenerAdapter(rabbitMQUserConsumer, "handleMessage");
//    }

}
