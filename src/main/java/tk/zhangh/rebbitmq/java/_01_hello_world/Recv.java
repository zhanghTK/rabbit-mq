package tk.zhangh.rebbitmq.java._01_hello_world;

import com.rabbitmq.client.*;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv)
            throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明queue，确保Recv先运行时程序不发生异常
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // DefaultConsumer缓存服务器推送的消息
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
