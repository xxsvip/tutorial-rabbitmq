package vip.xingxiaoshuai.rabbitmq.basic.mode.topic;

import com.rabbitmq.client.*;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;

public class Consumer1 {


    public static void main(String[] args) throws Exception {


        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("====================consumer1 begin================================");
                System.out.println("routing-key:" + envelope.getRoutingKey());
                System.out.println("交换机：" + envelope.getExchange());
                System.out.println("消息ID：" + envelope.getDeliveryTag());

                System.out.println("接收到的消息：" + new String(body, "UTF-8"));

                System.out.println("=======================consumer1 end=============================");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };


        channel.basicConsume(Producer.TOPIC_QUEUE_1,true,consumer);


        //不需要关闭资源，应该一直监听消息。




    }

}
