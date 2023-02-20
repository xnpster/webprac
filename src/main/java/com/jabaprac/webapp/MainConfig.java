package com.jabaprac.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MainConfig {
    static int cnt2 = 0;
//    @Bean
    public Worker workerBean() {
        cnt2++;
        return new Worker(cnt2);
    }
}
