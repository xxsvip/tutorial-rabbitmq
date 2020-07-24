package vip.xingxiaoshuai.rabbitmq.springboot.consumer.entity;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class SendMessage implements Serializable {


    private Integer id;

    private String username;

    private Integer age;

    private BigDecimal salary;




}
