package vn.vnpay.oraclejdbc.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.vnpay.oraclejdbc.rabbit_mq.Receiver;

@Configuration
public class RabbitMQConfig {

//    @Value("${rabbitMQ.topicExchangeName}")
//    private String topicExchangeName;

//    @Value("${rabbitMQ.queueName}")
//    private String queueName;

//    @Value("${rabbitMQ.routingKey}")
//    private String routingKey;

    @Bean
    Queue queue() {
        return new Queue("tien.test.qrcode.2", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("exchange-one");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("routing.*");
    }


//    @Bean
//    FanoutExchange fanoutExchange(){
//        return new FanoutExchange("exchange-one");
//    }
//
//    @Bean
//    Binding bindingFanoutExchange(Queue queue, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queue).to(fanoutExchange);
//    }


//    @Bean
//    DirectExchange directExchange(){
//        return new DirectExchange("exchange-one");
//    }
//
//    @Bean
//    Binding bindingDirectExchange(Queue queue,  DirectExchange directExchange) {
//        return BindingBuilder.bind(queue).to(directExchange).with("routing.one");
//    }



//    @Bean
//    HeadersExchange headersExchange(){
//        return new HeadersExchange("exchange-one");
//    }
//
//    @Bean
//    Binding bindingHeaderExchange(Queue queue, HeadersExchange headersExchange){
//        return BindingBuilder.bind(queue).to(headersExchange).where("name-control").matches("yourself");
//    }



    @Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                  MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer simpleMessageListenerContainer =
                new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueueNames("tien.test.qrcode.2");
        simpleMessageListenerContainer.setMessageListener(messageListenerAdapter);
        return simpleMessageListenerContainer;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}
