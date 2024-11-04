package com.reamillo.emplms.model;



import java.math.BigDecimal;

import lombok.Data;

@Data

public class Cart {
    private int id;
    private String name;
    
    private int productId;
    private String username;
    private String description;
    private BigDecimal pricePerItem;
    private BigDecimal totalPrice;
    private int quantity;  
}