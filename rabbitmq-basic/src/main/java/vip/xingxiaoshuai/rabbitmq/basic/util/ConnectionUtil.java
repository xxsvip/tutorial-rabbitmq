package vip.xingxiaoshuai.rabbitmq.basic.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {


    public static Connection getConnection() throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置主机地址
        connectionFactory.setHost("182.92.193.150");
        //设置端口
        connectionFactory.setPort(5672);
        //设置虚拟主机
        connectionFactory.setVirtualHost("/test");
        //设置用户名
        connectionFactory.setUsername("xxs");
        //设置密码
        connectionFactory.setPassword("123gogogo");

        return connectionFactory.newConnection();



    }

}
