package chen.practice.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        System.out.println("Consumer Application Starter ...");
        SpringApplication.run(ConsumerApplication.class, args);
        System.out.println("Consumer Application Starter Successful!");    }

}
