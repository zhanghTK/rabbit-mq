package tk.zhangh.rabbitmq.tut1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by ZhangHao on 2017/8/15.
 */
@Profile({"tut1", "hello-world"})
@Configuration
public class Tut1Config {
    @Bean
    public Queue hello() {
        return new Queue("amqp-hello");
    }

    @Profile("receiver")
    @Bean
    public Tut1Receiver receiver() {
        return new Tut1Receiver();
    }

    @Profile("sender")
    @Bean
    public Tut1Sender sender() {
        return new Tut1Sender();
    }
}
