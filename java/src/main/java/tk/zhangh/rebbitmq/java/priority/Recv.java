package tk.zhangh.rebbitmq.java.priority;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import static tk.zhangh.rebbitmq.java.priority.Sender.QUEUE;

/**
 * Created by ZhangHao on 2017/8/21.
 */
public class Recv {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            System.out.println(new String(delivery.getBody()));
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
