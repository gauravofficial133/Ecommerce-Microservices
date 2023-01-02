package com.gaurav.inventoryservice;

import com.gaurav.inventoryservice.models.Inventory;
import com.gaurav.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication implements CommandLineRunner {

	@Autowired
	private InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Inventory inventory = new Inventory();
//		inventory.setSkuCode("IPHONE_13");
//		inventory.setQuantity(100);
//
//		Inventory inventory1 = new Inventory();
//		inventory1.setSkuCode("IPHONE_12");
//		inventory1.setQuantity(50);
//
//		inventoryRepository.save(inventory);
//		inventoryRepository.save(inventory1);
	}
}
