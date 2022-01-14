package org.springframework.samples.petclinic.sfg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.YannyWordProducer;

@Configuration
public class YannyConfig {
    @Bean
    YannyWordProducer yannyWordProducer(){
        return new YannyWordProducer();
    }
}
