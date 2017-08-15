package tk.zhangh.rebbitmq.java._05_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.util.UUID;

/**
 * Created by ZhangHao on 2017/6/9.
 */
public class EmitLogTopic {
    private static final String EXCHANGE_NAME = "topic_logs";
    private static String[] ROUTING_KEYS = new String[]{"kernel.info", "cron.warning", "auth.info", "kernel.critical"};

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

//        for (int i = 0; i < 13500; i++) {
            for (String routingKey : ROUTING_KEYS) {
                String msg = UUID.randomUUID().toString();
                channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
                System.out.println(" [x] Sent '" + routingKey + "':'" + msg + "'");
            }
//        }

        channel.close();
        connection.close();
    }
}
