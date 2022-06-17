package com.pearldonut.findhomes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages= {"com.pearldonut.findhomes.mapper"})
public class HappyhouseFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyhouseFrameworkApplication.class, args);
	}

}
