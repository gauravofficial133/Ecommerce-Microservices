package com.gaurav.orderservice.services;

import com.gaurav.orderservice.dtos.InventoryResponse;
import com.gaurav.orderservice.dtos.OrderLineItemsDto;
import com.gaurav.orderservice.dtos.OrderRequest;
import com.gaurav.orderservice.events.OrderPlacedEvents;
import com.gaurav.orderservice.models.Order;
import com.gaurav.orderservice.models.OrderLineItems;
import com.gaurav.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private Tracer tracer;

    @Autowired
    private KafkaTemplate<String,OrderPlacedEvents> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).collect(Collectors.toList());
        order.setOrderLineItems(orderLineItems);

        List<String> skuCodesList = order.getOrderLineItems().stream().map(OrderLineItems::getSkuCode).toList();

        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        try (Tracer.SpanInScope ifSpanInScope = tracer.withSpan(inventoryServiceLookup.start())) {

            InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode",skuCodesList).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            assert inventoryResponses != null;
            boolean allProductsInStock = Arrays.stream(inventoryResponses)
                    .allMatch(InventoryResponse::isInStock);

            if(allProductsInStock){
                Order savedOrder = orderRepository.save(order);
                kafkaTemplate.send("orderPlacedTopic",new OrderPlacedEvents(savedOrder.getOrderNumber()));
                return "Order placed successfully";
            } else {
                throw new IllegalArgumentException("Product out of stock ....");
            }

        } finally {
            inventoryServiceLookup.end();
        }




    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
