package com.gaurav.orderservice.repository;

import com.gaurav.orderservice.models.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {
}