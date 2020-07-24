package vip.xingxiaoshuai.rabbitmq.springboot.producer.callback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/*
消息发送到交换机监听类
 */
@Slf4j
@Component
public class SendConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("Success... 消息成功发送到交换机! correlationData:{}", correlationData);
        } else {
            log.info("Fail... 消息发送到交换机失败! correlationData:{}", correlationData);
        }
    }
}

