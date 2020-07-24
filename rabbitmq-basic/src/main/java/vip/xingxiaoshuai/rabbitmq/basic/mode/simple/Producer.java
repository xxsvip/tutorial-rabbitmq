package vip.xingxiaoshuai.rabbitmq.basic.mode.simple;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;




public class Producer {

    static final String QUENE_NAME = "simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {


        Connection connection = ConnectionUtil.getConnection();
        //创建频道
        Channel channel = connection.createChannel();

        //声明（创建）队列
        /*
        参数一：队列名称
        参数二：是否持久化队列
        参数三：是否独占本次连接(一个channel独占一个connection)
        参数四：是否在不使用的时候自动删除队列
        参数五：队列其他参数
         */

        //第一次生产数据完成后，之后就没有必要每次都生成新的队列。
        channel.queueDeclare(QUENE_NAME,true,false,false,null);

        //发送消息
        String message = "我是第301条消息";
        /*
        参数一：交换机名称
        参数二：routingKey名称
        参数三：属性
        参数四：内容
         */
        channel.basicPublish("",QUENE_NAME,null,message.getBytes());


        System.out.println("已发送消息："+message);


        //释放资源
        channel.close();
        connection.close();











    }

}
