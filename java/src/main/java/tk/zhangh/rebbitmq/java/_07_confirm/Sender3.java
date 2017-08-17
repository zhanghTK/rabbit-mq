package tk.zhangh.rebbitmq.java._07_confirm;

import com.rabbitmq.client.*;
import lombok.AllArgsConstructor;

import java.io.IOException;

/**
 * Created by ZhangHao on 2017/8/16.
 */
@AllArgsConstructor
public class Sender3 {
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
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack, deliveryTag:" + deliveryTag + ", multiple:" + multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("nack, deliveryTag:" + deliveryTag + ", multiple:" + multiple);
            }
        });
    }
}
