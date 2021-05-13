package chen.practice.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        System.out.println("Provider Application Starter ...");
        SpringApplication.run(ProviderApplication.class, args);
        System.out.println("Provider Application Starter Successful!");
    }

}
