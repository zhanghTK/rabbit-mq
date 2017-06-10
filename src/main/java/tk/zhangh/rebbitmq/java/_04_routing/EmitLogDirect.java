package tk.zhangh.rebbitmq.java._04_routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "ex_logs_direct";
    private static final String[] SEVERITIES = {"info", "warning", "error"};

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明转发器的类型, 使用direct类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        sendMessage(channel);

        channel.close();
        connection.close();
    }

    private static void sendMessage(Channel channel) throws IOException {
        for (int i = 0; i < 10; i++) {
            String severity = getSeverity();
            String message = severity + "_log :" + UUID.randomUUID().toString();
            // 发布消息至转发器，指定routing key
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

    private static String getSeverity() {
        Random random = new Random();
        int ranVal = random.nextInt(3);
        return SEVERITIES[ranVal];
    }
}
