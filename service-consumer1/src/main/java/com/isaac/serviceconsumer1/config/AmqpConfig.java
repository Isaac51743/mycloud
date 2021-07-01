package com.isaac.serviceconsumer1.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
public class AmqpConfig {

    //set message converter
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    //create a queue
    @Bean
    public Queue myQueue1() {
        return new Queue("my queue 1");
    }

    @Bean
    public Queue myQueue2() {
        // using builder design pattern
        return QueueBuilder.durable("my queue 2").autoDelete().build();
    }

    // create am exchange
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Exchange myExchange1() {
        // using builder design pattern
        return ExchangeBuilder.topicExchange("my topic exchange 1").autoDelete().durable(true).build();
    }
    @Bean
    public Exchange myExchange2() {
        return new TopicExchange("my topic exchange 2");
    }

    // create a binding
    @Bean
    public Binding queueBinding1() {
        return BindingBuilder.bind(myQueue2()).to(myExchange2()).with("m.#").noargs();
    }

    @Bean
    public Binding queueBinding2() {
        // using builder design builder
        return new Binding("my queue 1", Binding.DestinationType.QUEUE, "my topic exchange 1", "c.d", null);
    }

      // create a connection
//    @Bean
//    ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
//        connectionFactory.setUsername("Isaac");
//        connectionFactory.setPassword("isaacpass");
//        return connectionFactory;
//    }

      // set the RabbitTemplate
//    @Bean
//    public AmqpTemplate template() {
//        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
//        rabbitTemplate.setMessageConverter(converter());
//        return rabbitTemplate;
//    }
}
