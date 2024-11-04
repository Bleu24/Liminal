package com.reamillo.emplms.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

    private int id;
    private String name;
    
    private String username;
    private int productId;
    private int quantity;
    private BigDecimal pricePerItem;
    private LocalDateTime orderDate;
    private BigDecimal totalPrice;
}
