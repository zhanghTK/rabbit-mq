package tk.zhangh.rebbitmq.java._09_ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

/**
 * Created by ZhangHao on 2017/8/21.
 */
public class MsgTtl {

    private static final String QUEUE_NAME = "ttl-msg";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, true, true, null);

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.deliveryMode(2);
        builder.expiration("10000");
        AMQP.BasicProperties properties = builder.build();

        channel.basicPublish("", QUEUE_NAME, properties, "ttl msg".getBytes());
    }
}
