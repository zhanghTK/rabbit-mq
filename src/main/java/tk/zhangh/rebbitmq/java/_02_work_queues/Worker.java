package tk.zhangh.rebbitmq.java._02_work_queues;

import com.rabbitmq.client.*;
import tk.zhangh.rebbitmq.java.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by ZhangHao on 2017/6/8.
 */
public class Worker {
    private final static String QUEUE_NAME = "workqueue";

    public static void main(String[] argv) throws java.io.IOException,
            java.lang.InterruptedException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        // durable：队列持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // 不要在同一时间给一个消费者超过一条消息,确保消费者负载公平
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                doWork(message);
                System.out.println(" [x] Done");
                // 任务处理后再发送ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // autoAck：接收到消息后自动ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.')
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
