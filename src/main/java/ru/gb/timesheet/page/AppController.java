package ru.gb.timesheet.page;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppController {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
