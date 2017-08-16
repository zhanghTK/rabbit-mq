package tk.zhangh.rebbitmq.java._07_confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.AllArgsConstructor;

/**
 * Created by ZhangHao on 2017/8/16.
 */
@AllArgsConstructor
public class Sender2 {
    private ConnectionFactory factory;
    private int count;
    private String exchangeName;
    private String queueName;
    private String routingKey;

    public void run() throws Exception {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "direct", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);
        channel.confirmSelect();
        for (int i = 0; i < count; i++) {
            channel.basicPublish(exchangeName, routingKey, MessageProperties.PERSISTENT_BASIC, Integer.toString(i).getBytes());
        }
        if (channel.waitForConfirms()) {
            System.out.println("[x] send " + count + "success");
        }
    }
}
