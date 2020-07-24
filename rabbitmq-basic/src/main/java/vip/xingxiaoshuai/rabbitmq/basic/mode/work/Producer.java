package vip.xingxiaoshuai.rabbitmq.basic.mode.work;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import vip.xingxiaoshuai.rabbitmq.basic.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    static final String QUENE_NAME = "work_queue";

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
        channel.queueDeclare(QUENE_NAME,true,false,false,null);

        //发送消息
        for (int i = 0; i < 30; i++) {
            String message = "插入第"+(i+1)+"条数据";
        // 参数一：交换机名称
        // 参数二：routingKey名称
            channel.basicPublish("",QUENE_NAME,null,message.getBytes());
            System.out.println("已发送消息："+message);
        }





        //释放资源
        channel.close();
        connection.close();











    }

}
