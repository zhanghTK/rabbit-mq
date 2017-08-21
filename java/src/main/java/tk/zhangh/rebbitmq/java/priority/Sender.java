package tk.zhangh.rebbitmq.java.priority;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangHao on 2017/8/21.
 */
public class Sender {

    protected static final String EXCHANGE = "priority_exchange";
    protected static final String QUEUE = "priority_queue";
    protected static final String ROUTING_KEY = "priority_key";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE, "direct", false);
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-max-priority", 10);
        channel.queueDeclare(QUEUE, false, false, false, arg);
        channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY);

        for (int i = 0; i < 10; i++) {
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            if (i % 2 != 0) {
                builder.priority(5);
            }
            AMQP.BasicProperties properties = builder.build();
            channel.basicPublish(EXCHANGE, ROUTING_KEY, properties, Integer.toString(i).getBytes());
        }

        channel.close();
        connection.close();
    }
}
