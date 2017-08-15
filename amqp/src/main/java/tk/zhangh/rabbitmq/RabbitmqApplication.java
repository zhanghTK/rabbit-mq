package tk.zhangh.rabbitmq;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Created by ZhangHao on 2017/8/15.
 */
@SpringBootApplication
@EnableScheduling
public class RabbitmqApplication {

    @Profile("usage_message")
    @Bean
    public CommandLineRunner usage() {
        return arg0 -> {
            System.out.println("This app uses Spring Profiles to control its behavior.");
            System.out.println("Sample usage: java -jar rabbit-tutorials.jar --spring.profiles.active=hello-world,sender");
        };
    }

    @Profile("!usage_message")
    @Bean
    public CommandLineRunner tutorial() {
        return new RabbitAmqpTutorialsRunner();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RabbitmqApplication.class, args);
    }
}
