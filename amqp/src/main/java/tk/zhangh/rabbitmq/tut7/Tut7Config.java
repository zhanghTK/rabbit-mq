package tk.zhangh.rabbitmq.tut7;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by ZhangHao on 2017/8/16.
 */
@Profile({"tut7", "confirm"})
@Configuration
public class Tut7Config {
    @Bean
    public DirectExchange topic() {
        return new DirectExchange("confirmExchange");
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(DirectExchange direct, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(direct).with("confirmRoutingKey");
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("zh-home.tk", 5672);
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, b, s)
                -> System.out.println("[confirm] correlationData:" + correlationData + ",b:" + b + ",s" + s));

        rabbitTemplate.setReturnCallback((message, i, s, s1, s2)
                -> System.out.println("[returnedMessage] message:" + message + ",i:" + i + ",s:" + s + ",s1" + s1 + ",s2" + s2));

        return rabbitTemplate;
    }

    @Profile("sender")
    @Bean
    public Tut7Sender sender() {
        return new Tut7Sender();
    }
}
