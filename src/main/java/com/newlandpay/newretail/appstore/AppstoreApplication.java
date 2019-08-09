package com.newlandpay.newretail.appstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = {"com.newlandpay.newretail.appstore.filter"})
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.newlandpay.newretail.appstore.biz.dao"})
@SpringBootApplication
public class AppstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppstoreApplication.class, args);
	}

}

