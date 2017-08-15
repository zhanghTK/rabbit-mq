package tk.zhangh.rebbitmq.java._04_routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import tk.zhangh.rebbitmq.java.RabbitConfig;

/**
 * 创建一个匿名队列，接收所有路由键的消息
 * Created by ZhangHao on 2017/6/8.
 */
public class ReceiveLogsDirect2 {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String[] SEVERITIES = {"info", "warning", "error"};
    private static int count = 0;

    public static void main(String[] argv) throws Exception {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明direct类型转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 创建随机队列
        String queueName = channel.queueDeclare().getQueue();

        // 指定binding_key和routing key
        for (String severity : SEVERITIES) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
        System.out.println(" [*] Waiting for all logs. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "',count:" + count++);
        }
    }
}
