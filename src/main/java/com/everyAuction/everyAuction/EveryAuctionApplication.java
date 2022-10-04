package com.everyAuction.everyAuction;

import com.everyAuction.everyAuction.Service.scheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EveryAuctionApplication {

	static scheduleService scheduleService;
	@Autowired
	public EveryAuctionApplication(scheduleService t) {
		this.scheduleService = t;
	}
	public static void main(String[] args) {
		SpringApplication.run(EveryAuctionApplication.class, args);
		scheduleService.start();
	}

}
