package com.flj;

import com.flj.kafka.Producer;
import com.flj.kafka.SampleMessage;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafakaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafakaTestApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(Producer producer) {
		return (args) -> producer.send(new SampleMessage(1, "A simple test message"));
	}
}
