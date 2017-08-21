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
public class MsgQueueTtl {

    private static final String QUEUE_NAME = "ttl-msg-queue";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> arg = new HashMap<>();
        arg.put("x-message-ttl", 600000000);
        channel.queueDeclare(QUEUE_NAME, false, true, true, arg);
    }
}
