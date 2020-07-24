package vip.xingxiaoshuai.rabbitmq.springboot.consumer.consumer;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import vip.xingxiaoshuai.rabbitmq.springboot.consumer.entity.SendMessage;

import java.io.IOException;
import java.util.Random;

@Component
@PropertySource("classpath:rabbitmq.properties")
@RabbitListener(queues = "${queue2}")
public class SecondConsumer {


    @Autowired
    private ObjectMapper objectMapper;

    @RabbitHandler
    public void process(String msg, Channel channel, Message message) throws IOException {


        Random random = new Random();
        int value = random.nextInt(2);


        System.out.println(value+"  receive entity:");
        System.out.println(objectMapper.readValue(msg, SendMessage.class));

        if(value == 0){
            // 业务处理成功后调用，消息会被确认消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }

        if(value == 1){
            // 业务处理失败后调用
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false, true);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }

}
