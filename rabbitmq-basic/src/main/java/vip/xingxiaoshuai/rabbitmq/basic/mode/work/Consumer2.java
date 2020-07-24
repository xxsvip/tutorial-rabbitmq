package vip.xingxiaoshuai.rabbitmq.basic.mode.work;

import com.rabbitmq.client.*;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2 {


    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /*
            consumerTag:消费者标签
            envelope:消息包相关内容（可以获取消息id，消息routingkey，交换机等）
            properties：消息属性
            body：消息
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("====================consumer2 begin================================");
                System.out.println("routing-key:" + envelope.getRoutingKey());
                System.out.println("交换机：" + envelope.getExchange());
                System.out.println("消息ID：" + envelope.getDeliveryTag());

                System.out.println("接收到的消息：" + new String(body, "UTF-8"));

                System.out.println("=======================consumer2 end=============================");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };


        //监听消息
        /*
        参数一：队列名称
        参数二：是否自动确认，设置为true表示消息接收到自动像mq回复接收到了，mq接收到回复后删除消息；设置为false则需要手动确认。

         */

        channel.basicConsume(Producer.QUENE_NAME,true,consumer);


        //不需要关闭资源，应该一直监听消息。




    }

}
