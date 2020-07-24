package vip.xingxiaoshuai.rabbitmq.basic.mode.routing;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    //交换机名称
    static final String DIRECT_EXCHANGE = "direct_exchange";
    //队列名称1
    static final String DIRECT_QUEUE_INSERT = "direct_queue_insert";
    //队列名称2
    static final String DIRECT_QUEUE_UPDATE = "direct_queue_update";


    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();

        //声明交换机
        /*
        参数一：交换机名称
        参数二：交换机类型 fanout direct topic headers
         */
        channel.exchangeDeclare(DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);



        //声明队列
        channel.queueDeclare(DIRECT_QUEUE_INSERT,true,false,false,null);
        channel.queueDeclare(DIRECT_QUEUE_UPDATE,true,false,false,null);


        //队列和交换机绑定
        //参数三：routing-key,routing-key来表示绑定关系。
        channel.queueBind(DIRECT_QUEUE_INSERT,DIRECT_EXCHANGE,"insert");
        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update");

        channel.queueBind(DIRECT_QUEUE_UPDATE,DIRECT_EXCHANGE,"update2");


        String message = "新增消息。。。";
        channel.basicPublish(DIRECT_EXCHANGE,"insert",null,message.getBytes());
        System.out.println("已发送消息："+message);

        message = "修改消息。。。";
        channel.basicPublish(DIRECT_EXCHANGE,"update",null,message.getBytes());
        System.out.println("已发送消息："+message);


        message = "修改消息2。。。";
        channel.basicPublish(DIRECT_EXCHANGE,"update2",null,message.getBytes());
        System.out.println("已发送消息："+message);





        //释放资源
        channel.close();
        connection.close();











    }

}
