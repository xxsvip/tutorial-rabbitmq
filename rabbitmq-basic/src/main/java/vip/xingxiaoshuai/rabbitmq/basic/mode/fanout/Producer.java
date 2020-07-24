package vip.xingxiaoshuai.rabbitmq.basic.mode.fanout;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    //交换机名称
    static final String FANOUT_EXCHANGE = "fanout_exchange";
    //队列名称1
    static final String FANOUT_QUEUE_1 = "fanout_queue_1";
    //队列名称1
    static final String FANOUT_QUEUE_2 = "fanout_queue_2";


    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();

        //声明交换机
        /*
        参数一：交换机名称
        参数二：交换机类型 fanout direct topic headers
         */
        channel.exchangeDeclare(FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);



        //声明队列
        channel.queueDeclare(FANOUT_QUEUE_1,true,false,false,null);
        channel.queueDeclare(FANOUT_QUEUE_2,true,false,false,null);


        //队列和交换机绑定
        //参数三：routing-key,routing-key来表示绑定关系。
        channel.queueBind(FANOUT_QUEUE_1,FANOUT_EXCHANGE,"");
        channel.queueBind(FANOUT_QUEUE_2,FANOUT_EXCHANGE,"");

        //发送消息
        //参数二：routing-key
        for (int i = 0; i < 30; i++) {
            String message = "插入第"+(i+1)+"条数据";
            channel.basicPublish(FANOUT_EXCHANGE,"",null,message.getBytes());
            System.out.println("已发送消息："+message);
        }





        //释放资源
        channel.close();
        connection.close();











    }

}
