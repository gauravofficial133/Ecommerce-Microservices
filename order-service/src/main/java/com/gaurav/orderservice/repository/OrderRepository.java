package com.gaurav.orderservice.repository;

import com.gaurav.orderservice.models.Order;
import com.gaurav.orderservice.models.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

}