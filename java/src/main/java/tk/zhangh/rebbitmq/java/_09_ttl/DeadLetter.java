package tk.zhangh.rebbitmq.java._09_ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangHao on 2017/8/21.
 */
public class DeadLetter {
    private static final String QUEUE_NAME = "test-dead-letter";

    private static final String DEAD_EXCHANGE = "dead_exchange";

    private static final String DEAD_ROUTING_KEY = "dead_routing_ley";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 创建处理死信的 exchange，queue，binding
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct", false, true, null);
        String deadQueue = channel.queueDeclare().getQueue();
        channel.queueBind(deadQueue, DEAD_EXCHANGE, DEAD_ROUTING_KEY);

        // 正常消息发送指定死信处理
        Map<String, Object> arg = new HashMap<>();
        arg.put("x-message-ttl", 10000);
        arg.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arg.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);

        channel.queueDeclare(QUEUE_NAME, false, true, true, arg);

        channel.basicPublish("", QUEUE_NAME, null, "ttl msg".getBytes());
    }
}
