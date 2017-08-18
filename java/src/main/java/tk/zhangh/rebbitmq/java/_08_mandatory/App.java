package tk.zhangh.rebbitmq.java._08_mandatory;

import com.rabbitmq.client.ConnectionFactory;
import tk.zhangh.rebbitmq.java.RabbitConfig;

/**
 * Created by ZhangHao on 2017/8/18.
 */
public class App {
    public static void main(String[] args) throws Exception {
        String exchangeName = "mandatoryExchange";
        String queueName = "";
        String routingKey = "mandatoryRoutingKey";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);

        new Sender(factory, exchangeName, queueName, routingKey).run();
    }
}
