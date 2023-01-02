package com.gaurav.inventoryservice.services;

import com.gaurav.inventoryservice.dtos.InventoryResponse;
import com.gaurav.inventoryservice.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;


    public List<InventoryResponse> isInStock(List<String> skuCode) throws InterruptedException {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
