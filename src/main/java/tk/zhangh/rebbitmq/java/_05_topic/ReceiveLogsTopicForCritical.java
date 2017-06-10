package tk.zhangh.rebbitmq.java._05_topic;

import com.rabbitmq.client.*;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.io.IOException;

/**
 * Created by ZhangHao on 2017/6/9.
 */
public class ReceiveLogsTopicForCritical {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        channel.queueBind(queueName, EXCHANGE_NAME, "*.critical");

        System.out.println(" [*] Waiting for critical messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received routingKey = " + envelope.getRoutingKey() + ",msg = " + message + ".");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
