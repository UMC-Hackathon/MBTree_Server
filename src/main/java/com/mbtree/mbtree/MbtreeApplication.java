package com.mbtree.mbtree;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MbtreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbtreeApplication.class, args);
	}

}
