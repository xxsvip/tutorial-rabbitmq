package vip.xingxiaoshuai.rabbitmq.basic.mode.topic;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    //交换机名称
    static final String TOPIC_EXCHANGE = "topic_exchange";
    //队列名称1
    static final String TOPIC_QUEUE_1 = "topic_queue_1";
    //队列名称2
    static final String TOPIC_QUEUE_2 = "topic_queue_2";


    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();

        //声明交换机
        /*
        参数一：交换机名称
        参数二：交换机类型 fanout direct topic headers
         */
        channel.exchangeDeclare(TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);



        //声明队列
        channel.queueDeclare(TOPIC_QUEUE_1,true,false,false,null);
        channel.queueDeclare(TOPIC_QUEUE_2,true,false,false,null);


        //队列和交换机绑定
        channel.queueBind(TOPIC_QUEUE_1,TOPIC_EXCHANGE,"item.#");
        channel.queueBind(TOPIC_QUEUE_2,TOPIC_EXCHANGE,"*.delete");






        String message = "新增消息。。。";
        channel.basicPublish(TOPIC_EXCHANGE,"item.insert",null,message.getBytes());
        System.out.println("已发送消息："+message);


        message = "修改消息。。。";
        channel.basicPublish(TOPIC_EXCHANGE,"item.update",null,message.getBytes());
        System.out.println("已发送消息："+message);

        message = "删除消息。。。";
        channel.basicPublish(TOPIC_EXCHANGE,"item.delete",null,message.getBytes());
        System.out.println("已发送消息："+message);






        //释放资源
        channel.close();
        connection.close();











    }

}
