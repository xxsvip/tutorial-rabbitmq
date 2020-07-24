package vip.xingxiaoshuai.rabbitmq.springboot.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.xingxiaoshuai.rabbitmq.springboot.producer.entity.SendMessage;

import java.math.BigDecimal;

@RestController
@PropertySource("classpath:rabbitmq.properties")
public class SendMsgController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${exchange1}")
    private String exchange1;
    @Value("${exchange2}")
    private String exchange2;

    private String SNED_STRING_KEY = "item.hello";
    private String SNED_ENTITY_KEY = "entity.insert";






    @GetMapping("/send/string")
    public String sendString(@RequestParam String msg){
        rabbitTemplate.convertAndSend(exchange1,SNED_STRING_KEY,msg);
        return "发送string消息成功";
    }

    @Autowired
    private ObjectMapper objectMapper;


    @GetMapping("/send/entity")
    public String sendEntity(Integer id) throws JsonProcessingException {
        SendMessage sendMessage = SendMessage.builder().id(id).age(28).salary(new BigDecimal(23.45)).username("邢小帅").build();

        CorrelationData correlationData = new CorrelationData(id.toString());

//        rabbitTemplate.convertAndSend(exchange2,SNED_ENTITY_KEY,objectMapper.writeValueAsString(sendMessage));
        rabbitTemplate.convertAndSend(exchange2,SNED_ENTITY_KEY,objectMapper.writeValueAsString(sendMessage),correlationData);

        return "发送entity消息成功";
    }

    @GetMapping("/send/test")
    public String test(Integer id,String exchange,String key) throws JsonProcessingException {

        SendMessage sendMessage = SendMessage.builder().id(id).age(28).salary(new BigDecimal(23.45)).username("邢小帅").build();

        CorrelationData correlationData = new CorrelationData(id.toString());

//        rabbitTemplate.convertAndSend(exchange2,SNED_ENTITY_KEY,objectMapper.writeValueAsString(sendMessage));
        rabbitTemplate.convertAndSend(exchange,key,objectMapper.writeValueAsString(sendMessage),correlationData);

        return "发送entity消息成功";
    }

}
