package tk.zhangh.rebbitmq.java._08_mandatory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.AllArgsConstructor;

/**
 * Created by ZhangHao on 2017/8/16.
 */
@AllArgsConstructor
public class Sender {
    private ConnectionFactory factory;
    private String exchangeName;
    private String queueName;
    private String routingKey;

    public void run() throws Exception {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, "direct", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        channel.basicPublish(exchangeName, routingKey, true, false,
                MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());

        channel.addReturnListener((replyCode, replyText, exchange, routingKey, basicProperties, body) ->
                System.out.println("[listener]replyCode:" + replyText + ", replyText:" + replyText
                        + ",exchange:" + exchange + ",routingKey:" + routingKey + ",basicProperties:"
                        + basicProperties.toString() + ".body:" + new String(body)));
    }
}
