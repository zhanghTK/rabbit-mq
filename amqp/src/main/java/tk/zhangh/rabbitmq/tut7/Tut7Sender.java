package tk.zhangh.rabbitmq.tut7;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by ZhangHao on 2017/8/16.
 */
public class Tut7Sender {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    private int index;

    private int count;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        if (++this.index == 2) {
            this.index = 0;
        }
        String key = Integer.toString(index);
        String message = Integer.toString(++this.count);
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
