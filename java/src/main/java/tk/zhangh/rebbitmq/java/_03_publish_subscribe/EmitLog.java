package tk.zhangh.rebbitmq.java._03_publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.time.LocalDateTime;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 设置转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message = LocalDateTime.now() + " : log something";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
