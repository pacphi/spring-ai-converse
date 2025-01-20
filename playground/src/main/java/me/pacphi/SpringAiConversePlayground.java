package me.pacphi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringAiConversePlayground {
    public static void main(String[] args) {
        SpringApplication.run(SpringAiConversePlayground.class, args);
    }
}
