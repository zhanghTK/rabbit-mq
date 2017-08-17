package tk.zhangh.rebbitmq.java._07_confirm;

import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

/**
 * Created by ZhangHao on 2017/8/16.
 */
public class App {
    public static void main(String[] args) throws Exception {
        String exchangeName = "confirmExchange";
        String queueName = "";
        String routingKey = "confirmRoutingKey";
        int count = 100;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);

        new Sender(factory, count, exchangeName, queueName, routingKey).run();

        new Sender2(factory, count, exchangeName, queueName, routingKey).run();

        new Sender3(factory, count, exchangeName, queueName, routingKey).run();

    }
}
