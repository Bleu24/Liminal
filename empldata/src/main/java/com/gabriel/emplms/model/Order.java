package com.gabriel.emplms.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

    private int id;
    private String name;
    private List<Cart> orderItems;
    private int productId;
    private String username;
    private BigDecimal pricePerItem;
    private BigDecimal totalPrice;
    private int quantity;  
}
