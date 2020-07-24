package vip.xingxiaoshuai.rabbitmq.springboot.producer.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import vip.xingxiaoshuai.rabbitmq.springboot.producer.callback.SendConfirmCallback;
import vip.xingxiaoshuai.rabbitmq.springboot.producer.callback.SendReturnCallback;

@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class RabbitMQConfig {


    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        //MQ给生产者发送接收确认消息
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback( new SendConfirmCallback());
        rabbitTemplate.setReturnCallback( new SendReturnCallback());

        return rabbitTemplate;
    }


    //声明队列
    @Value("${queue1}")
    private String QUEUE1;
    @Bean("queue1")
    public Queue getQueue1(){
        return QueueBuilder.durable(QUEUE1).build();
    }

    @Value("${queue2}")
    private String QUEUE2;
    @Bean("queue2")
    public Queue getQueue2(){
        return QueueBuilder.durable(QUEUE2).build();
    }






    //声明交换机
    @Value("${exchange1}")
    private String EXCHANGE1;
    @Bean("exchange1")
    public Exchange topicExchange(){
        return ExchangeBuilder.topicExchange(EXCHANGE1).durable(true).build();
    }

    @Value("${exchange2}")
    private String EXCHANGE2;
    @Bean("exchange2")
    public Exchange directExchange(){
        return ExchangeBuilder.directExchange(EXCHANGE2).durable(true).build();
    }


    //绑定队列和交换机
    @Bean
    public Binding binding(@Qualifier("queue1") Queue queue,@Qualifier("exchange1") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("item.#").noargs();
    }

    @Bean
    public Binding binding2(@Qualifier("queue2") Queue queue,@Qualifier("exchange2") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("entity.insert").noargs();
    }



}
