package com.shdh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 유레카 서버 등록.
public class CatalogsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogsServiceApplication.class, args);
	}

}
