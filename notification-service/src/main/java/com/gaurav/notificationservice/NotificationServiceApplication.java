package com.gaurav.notificationservice;

import com.gaurav.notificationservice.events.OrderPlacedEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@KafkaListener(topics = "orderPlacedTopic")
	public void handleNotification(OrderPlacedEvents orderPlacedEvents){
		log.info("Order with order id:{} has been placed successfully",orderPlacedEvents.getOrderNumber());
	}
}
