package vip.xingxiaoshuai.rabbitmq.springboot.consumer.consumer;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:rabbitmq.properties")
@RabbitListener(queues = "${queue1}" )
public class FirstConsumer {


    @RabbitHandler
    public void process(String msg){
        System.out.println("receive message: "+msg);
    }

}
