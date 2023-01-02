package com.gaurav.productservice.dtos;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String name;

    private String description;

    private BigDecimal price;
}
