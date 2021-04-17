package com.sepideh.notebook.config;

import com.sepideh.notebook.dto.user.SimpleUserDto;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfig {

    public static final String TOPIC_NAME_TEST = "topicTest";
    public static final String GROUP_ID_TEST = "groupTest";

    public static final String TOPIC_NAME_USER = "topicUser";
    public static final String GROUP_ID_USER = "groupUser";

    @Value(value = "${project.kafka.server.url}")
    private String bootstrapAddress;

    // Topic configurations ********************************************************************************************
//    @Bean
//    public KafkaAdmin kafkaAdmin() {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        return new KafkaAdmin(configs);
//    }
//
//    @Bean
//    public NewTopic topicTest() {
//        return new NewTopic(TOPIC_NAME_TEST, 1, (short) 1);
//    }
//
//    // Topic configurations For User ***********************************************************************************
//    @Bean
//    public NewTopic topicUser() {
//        return new NewTopic(TOPIC_NAME_USER, 1, (short) 1);
//    }
//
//    // Producer Configuration ******************************************************************************************
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//
//        configProps.put(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress
//        );
//        configProps.put(
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class
//        );
//        configProps.put(
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class
//        );
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate(
//        ProducerFactory<String, String> producerFactory
//    ) {
//        return new KafkaTemplate<>(producerFactory);
//    }
//
//    // Producer Configuration For Publish User *************************************************************************
//    @Bean
//    public ProducerFactory<String, SimpleUserDto> producerFactoryForUser() {
//        Map<String, Object> configProps = new HashMap<>();
//
//        configProps.put(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress
//        );
//        configProps.put(
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class
//        );
//        configProps.put(
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//            JsonSerializer.class
//        );
//
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, SimpleUserDto> kafkaTemplateForUser() {
//        return new KafkaTemplate<>(producerFactoryForUser());
//    }
//
//    // Consumer Configuration ******************************************************************************************
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress
//        );
//        props.put(
//            ConsumerConfig.GROUP_ID_CONFIG,
//            GROUP_ID_TEST
//        );
//        props.put(
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class
//        );
//        props.put(
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class
//        );
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
//        ConsumerFactory<String, String> consumerFactory
//    ) {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory =
//            new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(consumerFactory);
//
//        return factory;
//    }
//
//    // Consumer Configuration For User *********************************************************************************
//    @Bean
//    public ConsumerFactory<String, SimpleUserDto> consumerFactoryForUser() {
//        Map<String, Object> props = new HashMap<>();
//
//        props.put(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress
//        );
//        props.put(
//            ConsumerConfig.GROUP_ID_CONFIG,
//            GROUP_ID_USER
//        );
//
//        return new DefaultKafkaConsumerFactory<>(
//            props,
//            new StringDeserializer(),
//            new JsonDeserializer<>(SimpleUserDto.class, false)
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, SimpleUserDto> kafkaListenerContainerFactoryForUser() {
//        ConcurrentKafkaListenerContainerFactory<String, SimpleUserDto> factory =
//            new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(consumerFactoryForUser());
//        factory.setErrorHandler(mErrorHandler);
//
//        return factory;
//    }
//
//    //******************************************************************************************************************
//    private final ErrorHandler mErrorHandler = new ErrorHandler() {
//
//        @Override
//        public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer, MessageListenerContainer container) {
//            String error = thrownException.getMessage()
//                .split("Error deserializing key/value for partition ")[1]
//                .split(". If needed, please seek past the record to continue consumption.")[0];
//
//            String topics = error.split("-")[0];
//            int offset = Integer.valueOf(error.split("offset ")[1]);
//            int partition = Integer.valueOf(error.split("-")[1].split(" at")[0]);
//
//            TopicPartition topicPartition = new TopicPartition(topics, partition);
//            //log.info("Skipping " + topic + "-" + partition + " offset " + offset);
//            consumer.seek(topicPartition, offset + 1);
//            System.out.println("OK");
//        }
//
//        @Override
//        public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord) { }
//
//        @Override
//        public void handle(Exception e, ConsumerRecord<?, ?> consumerRecord, Consumer<?,?> consumer) {
//            String s = e.getMessage()
//                .split("Error deserializing key/value for partition ")[1]
//                .split(". If needed, please seek past the record to continue consumption.")[0];
//
//            String topics = s.split("-")[0];
//            int offset = Integer.valueOf(s.split("offset ")[1]);
//            int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);
//
//            TopicPartition topicPartition = new TopicPartition(topics, partition);
//            //log.info("Skipping " + topic + "-" + partition + " offset " + offset);
//            consumer.seek(topicPartition, offset + 1);
//            System.out.println("OK");
//        }
//
//    };

}
