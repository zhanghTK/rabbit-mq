package tk.zhangh.rebbitmq.java._02_work_queues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class NewTask {
    //队列名称
    private final static String QUEUE_NAME = "workqueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // durable：队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        sendMessage(channel);

        channel.close();
        connection.close();

    }

    private static void sendMessage(Channel channel) throws IOException {
        //发送10条消息，依次在消息后面附加1-10个点
        for (int i = 0; i < 10; i++) {
            String dots = "";
            for (int j = 0; j <= i; j++) {
                dots += ".";
            }
            String message = "helloworld" + dots + dots.length();
            // PERSISTENT_TEXT_PLAIN：消息持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
