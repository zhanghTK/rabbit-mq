package tk.zhangh.rebbitmq.java._05_topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import tk.zhangh.rebbitmq.java.RabbitConfig;

/**
 * Created by ZhangHao on 2017/6/9.
 */
public class RecvKernelTopic {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 随机生成一个队列
        String queueName = channel.queueDeclare().getQueue();

        // 接收所有与kernel相关的消息
        channel.queueBind(queueName, EXCHANGE_NAME, "kernel.*");

        System.out.println(" [*] Waiting for messages about kernel. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Kernel Received '" + routingKey + "':'" + message + "'");
        }
    }
}
