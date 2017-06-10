package tk.zhangh.rebbitmq.java._01_hello_world;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.util.concurrent.TimeoutException;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class Send {
    // queue name
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv)
            throws java.io.IOException, TimeoutException {
        // 创建 connection
        // connection抽象socket，负责协议版本的协商和认证等
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();

        // 创建 channel
        // channel包含大部分API
        Channel channel = connection.createChannel();

        // 声明 queue
        // queue声明是幂等的，已创建不会重复创建
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
